# python3 -m misc.python.best_podiums

from misc.python.util.time_util import time_format
from misc.python.util.html_util import get_competition_html_link, get_competitor_html_link
from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics

title = "Best podiums"

podium_length = 3

query = """
      select comp_id, c.name,
          (first_avg + second_avg + third_avg) avg_sum,
          first_id, first_name,
          first_avg,
          second_id, second_name,
          second_avg,
          third_id, third_name,
          third_avg
      from (
          select competitionId comp_id, 
              max(case when row_num=1 then personId end) first_id, 
              max(case when row_num=1 then personName end) first_name,
              max(case when row_num=1 then average end) first_avg, 
              max(case when row_num=2 then personId end) second_id, 
              max(case when row_num=2 then personName end) second_name, 
              max(case when row_num=2 then average end) second_avg, 
              max(case when row_num=3 then personId end) third_id, 
              max(case when row_num=3 then personName end) third_name, 
              max(case when row_num=3 then average end) third_avg
          from (
              select competitionId, personId, personName, average,
                  row_number() over (partition by competitionId order by average) row_num
              from Results
              where eventId = '%s' and roundTypeId in ('c', 'f') and average > 0
              ) final_podiums_without_ties
          group by competitionId
      ) podiums_pivotted
      inner join Competitions as c on podiums_pivotted.comp_id = c.id
      where third_avg is not null
      order by convert(avg_sum, float)
      limit 10
"""


def best_podiums():
    statistics = {}
    statistics["title"] = title
    statistics["group"] = "Results"
    statistics["statistics"] = []
    statistics["displayMode"] = "SELECTOR"

    headers = ["Competition", "Sum", "First",
               "Avg", "Second", "Avg", "Third", "Avg"]

    current_events = get_current_events()

    cnx = get_database_connection()
    cursor = cnx.cursor()

    for current_event in current_events:
        log.info(current_event.name)

        # Not an sql replacement
        cursor.execute(query % current_event.event_id)

        lines = cursor.fetchall()

        # Also skips 333mbf
        if not lines:
            continue

        stat = []
        for line in lines:

            result = [get_competition_html_link(
                line[0], line[1]), time_format(line[2], current_event.event_id, "average")]

            for i in range(podium_length):
                result.append(get_competitor_html_link(
                    line[3+3*i], line[4+3*i]))
                result.append(time_format(
                    line[5+3*i], current_event.event_id, "average"))

            stat.append(result)

        statistics["statistics"].append(
            {"keys": [current_event.name], "content": stat, "headers": headers, "showPositions": True, "positionTieBreakerIndex": 1})

    cnx.close()

    return statistics


def main():
    log.info(" ========== %s ==========" % title)

    statistics = best_podiums()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
