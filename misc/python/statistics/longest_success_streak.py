# python3 -m misc.python.statistics.longest_success_streak

import bisect

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.html_util import (get_competition_html_link,
                                        get_competitor_html_link)
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics

title = "Longest success streak"


class Competitor(Comp):
    def __init__(self, wca_id, name, country):
        super().__init__(wca_id)
        self.name = name
        self.country = country
        self.max = 0
        self.count = 0
        self.max_range_start = None
        self.max_range_end = None
        self.current_range_start = None


query = """select
    personId,
    personName,
    r.countryId,
    competitionId,
    value1,
    value2,
    value3,
    value4,
    value5
from
    Results r
inner join Competitions c on
	r.competitionId = c.id
inner join Events e on
	r.eventId = e.id
where
    eventId = %(event_id)s
order by
	start_date,
	e.`rank`"""


def longest_streaks():

    LIMIT = 50

    out = {}
    out["title"] = title
    out["group"] = "Results"
    out["displayMode"] = "SELECTOR"
    headers = ["Streak", "Person",
               "Country", "Streak Start", "Streak End"]
    out["statistics"] = []

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    for event in get_current_events():
        log.info(event.name)
        event_id = event.event_id

        competitors = []

        cursor.execute(query, {"event_id": event_id})

        for wca_id, person_name, country_id, competition_id, v1, v2, v3, v4, v5 in cursor:

            competitor = Competitor(wca_id, person_name, country_id)

            i = bisect.bisect_left(competitors, competitor)
            if i == len(competitors) or competitors[i] != competitor:
                competitors.insert(i, competitor)
            competitor = competitors[i]

            for x in [v1, v2, v3, v4, v5]:
                if x == -1:
                    competitor.count = 0
                elif x > 0:
                    competitor.count += 1

                    if competitor.count >= competitor.max:
                        competitor.max = competitor.count
                        competitor.max_range_start = competitor.current_range_start
                        competitor.max_range_end = competition_id

                    if competitor.count == 1:
                        competitor.current_range_start = competition_id

        competitors = sorted(competitors, key=lambda c: -c.max)

        table = []
        count = 1
        prev = None
        for competitor in competitors:
            streak = competitor.max
            if count > LIMIT and prev != streak:
                break

            current = competitor.count

            link = get_competitor_html_link(competitor.wca_id, competitor.name)
            table.append([streak, link, competitor.country,
                          get_competition_html_link(competitor.max_range_start), "-" if streak == current else get_competition_html_link(competitor.max_range_end)])

            count += 1
            prev = streak

        out["statistics"].append({"keys": [event.name], "content": table,
                                  "headers": headers, "showPositions": True, "positionTieBreakerIndex": 0})
    cnx.close()

    return out


def main():
    log.info("========== %s ==========" % title)
    statistics = longest_streaks()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
