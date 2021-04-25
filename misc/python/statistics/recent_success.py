# python3 -m misc.python.statistics.recent_success

import bisect
from misc.python.util.time_util import time_format
from dateutil.relativedelta import relativedelta
import datetime

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.html_util import (get_competition_html_link,
                                        get_competitor_html_link)
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics

title = "Recent success rate"


class Competitor(Comp):
    def __init__(self, wca_id, name, country):
        super().__init__(wca_id)
        self.name = name
        self.country = country
        self.attempts = 0
        self.rate = None
        self.results = []


query = """select
    personId,
    personName,
    r.countryId,
    value1,
    value2,
    value3,
    value4,
    value5
from
    Results r
    inner join Competitions c on r.competitionId = c.id
where
    eventId = %(event_id)s
    and c.start_date >= date(%(min_date)s)"""

custom_query = """select
    personName,
    format(solves / attempts, 2) Rate,
    concat(solves, ' / ', attempts) Successes
from
    (
        select
            personName,
            sum(
                IF(value1 > 0, 1, 0) + IF(value2 > 0, 1, 0) + IF(value3 > 0, 1, 0) + IF(value4 > 0, 1, 0) + IF(value5 > 0, 1, 0)
            ) solves,
            sum(
                IF(
                    value1 != -2
                    and value1 != 0,
                    1,
                    0
                ) + IF(
                    value2 != -2
                    and value2 != 0,
                    1,
                    0
                ) + IF(
                    value3 != -2
                    and value3 != 0,
                    1,
                    0
                ) + IF(
                    value4 != -2
                    and value4 != 0,
                    1,
                    0
                ) + IF(
                    value5 != -2
                    and value5 != 0,
                    1,
                    0
                )
            ) attempts
        from
            Results r
            inner join Competitions c on r.competitionId = c.id
        where
            c.start_date >= date('%(min_date)s')
            and personId = ':WCA_ID'
            and eventId = '%(event_id)s'
        group by
            personId,
            personName
    ) result
"""


def recent_success():

    LIMIT = 10
    MIN_SOLVES = 6

    min_date = datetime.date.today() - relativedelta(years=1)
    log.info("Min date: %s" % min_date)

    out = {}
    out["title"] = title
    out["explanation"] = "Since %s, minimum %s successes" % (
        min_date, MIN_SOLVES)
    out["group"] = "Results"
    out["displayMode"] = "SELECTOR"
    headers = ["Person", "Country", "Rate",
               "Success / Attempts", "Best", "Worst", "Average"]
    out["statistics"] = []

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    for event in get_current_events():
        log.info(event.name)
        event_id = event.event_id

        competitors = []

        log.info("Find results")
        cursor.execute(query, {"event_id": event_id, "min_date": min_date})
        for wca_id, person_name, country_id, v1, v2, v3, v4, v5 in cursor:

            competitor = Competitor(wca_id, person_name, country_id)

            i = bisect.bisect_left(competitors, competitor)
            if i == len(competitors) or competitors[i] != competitor:
                competitors.insert(i, competitor)
            competitor = competitors[i]

            for x in [v1, v2, v3, v4, v5]:
                if x == -1 or x > 0:
                    competitor.attempts += 1
                if x > 0:
                    competitor.results.append(x)

        log.info("Found %s competitors" % len(competitors))

        log.info("Discard competitors with few results")
        competitors = list(
            filter(lambda c: len(c.results) >= MIN_SOLVES, competitors))
        log.info("%s competitors left" % len(competitors))

        log.info("Fill rates")
        for competitor in competitors:
            competitor.rate = float(
                "%.2f" % (len(competitor.results) / competitor.attempts))

        log.info("Sort competitors")
        competitors = sorted(
            competitors, key=lambda c: [-c.rate, -len(c.results), -c.attempts, c.name])

        table = []
        count = 1
        prev = None
        for competitor in competitors:
            rate = competitor.rate
            if (count > LIMIT and prev != rate) or count > 2*LIMIT:  # We interrupt the list anyways
                break

            link = get_competitor_html_link(competitor.wca_id, competitor.name)
            table.append([link, competitor.country, "%.2f" % rate, "%s / %s" %
                          (len(competitor.results), competitor.attempts), time_format(min(competitor.results), event.event_id), time_format(max(competitor.results), event.event_id), time_format(sum(competitor.results)/len(competitor.results), event.event_id, "average")])

            count += 1
            prev = rate

        out["statistics"].append({"keys": [event.name], "content": table,
                                  "headers": headers, "showPositions": True, "positionTieBreakerIndex": 2, "sqlQueryCustom": custom_query % {"event_id": event_id, "min_date": min_date}})
    cnx.close()

    return out


def main():
    log.info("========== %s ==========" % title)
    statistics = recent_success()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
