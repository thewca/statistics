# python3 -m misc.python.statistics.best_podiums

from misc.python.util.time_util import time_format
from misc.python.util.html_util import (
    get_competition_html_link,
    get_competitor_html_link,
)
from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import (
    create_statistics,
    handle_statistics_control,
)

title = "Best podiums"

podium_length = 3

query = """
      select comp_id, c.name,
          case when event_id = '333mbf' then (99*3 - substring(first_result, 1, 2) - substring(second_result, 1, 2) - substring(third_result, 1, 2)) else first_result + second_result + third_result end avg_sum,
          first_id, first_name,
          first_result,
          second_id, second_name,
          second_result,
          third_id, third_name,
          third_result
      from (
          select competition_id comp_id, 
              max(case when row_num=1 then person_id end) first_id, 
              max(case when row_num=1 then person_name end) first_name,
              max(case when row_num=1 then best_results end) first_result, 
              max(case when row_num=2 then person_id end) second_id, 
              max(case when row_num=2 then person_name end) second_name, 
              max(case when row_num=2 then best_results end) second_result, 
              max(case when row_num=3 then person_id end) third_id, 
              max(case when row_num=3 then person_name end) third_name, 
              max(case when row_num=3 then best_results end) third_result,
              event_id
          from (
              select competition_id, person_id, person_name, case when event_id in ('333bf', '444bf', '555bf', '333mbf') then best else average end best_results, event_id,
                  row_number() over (partition by competition_id order by case when event_id in ('333bf', '444bf', '555bf', '333mbf') then best else average end) row_num
              from results
              where event_id = '%s' and round_type_id in ('c', 'f') and (case when event_id in ('333bf', '444bf', '555bf', '333mbf') then best else average end) > 0
              ) final_podiums_without_ties
          group by competition_id
      ) podiums_pivotted
      inner join competitions as c on podiums_pivotted.comp_id = c.id
      where third_result is not null
      order by case when event_id = '333mbf' then avg_sum end desc, case when event_id != '333mbf' then convert(avg_sum, float) end
      limit 10
"""


def best_podiums():
    statistics = {}
    statistics["title"] = title
    statistics["groupName"] = "Results"
    statistics["statistics"] = []
    statistics["displayMode"] = "SELECTOR"

    headers = [
        "Competition",
        "Sum",
        "First",
        "Result",
        "Second",
        "Result",
        "Third",
        "Result",
    ]

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

            result = [
                get_competition_html_link(line[0], line[1]),
                (
                    str(int(line[2]))
                    if current_event.event_id == "333mbf"
                    else time_format(line[2], current_event.event_id, "average")
                ),
            ]

            # Each competitor
            for i in range(podium_length):
                result.append(
                    get_competitor_html_link(line[3 + 3 * i], line[4 + 3 * i])
                )
                result.append(
                    time_format(line[5 + 3 * i], current_event.event_id, "average")
                )

            stat.append(result)

        statistics["statistics"].append(
            {
                "keys": [current_event.name],
                "content": stat,
                "headers": headers,
                "showPositions": True,
                "positionTieBreakerIndex": 1,
            }
        )

    cnx.close()

    return statistics


@handle_statistics_control
def main():
    log.info(" ========== %s ==========" % title)

    statistics = best_podiums()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
