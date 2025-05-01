# From the root
# python3 -m misc.python.statistics.ranges

import bisect

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.html_util import get_competitor_html_link
from misc.python.util.log_util import log
from misc.python.util.mbld_util import get_mbld_points
from misc.python.util.range_util import largest_range
from misc.python.util.statistics_api_util import (
    create_statistics,
    handle_statistics_control,
)
from misc.python.util.time_util import time_format


class Competitor(Comp):
    def __init__(self, wca_id, name, country) -> None:
        super().__init__(wca_id)
        self.name = name
        self.country = country
        self.results = []
        self.range = None
        self.range_start = None
        self.range_end = None


title = "Ranges"

query = """select
	person_id,
	person_name,
	country_id,
	value1,
	value2,
	value3,
	value4,
	value5
from
	Results
where
	eventId = '%s'
"""


def has_multiple_results(results):
    if len(results) < 2:
        return False
    for i in range(1, len(results)):
        if results[i] != results[i - 1]:
            return True
    return False


def ranges():
    LIMIT = 10

    out = {}
    out["title"] = title
    out["groupName"] = "Results"
    out["displayMode"] = "SELECTOR"
    headers = ["Person", "Range Size", "Country", "Range Start", "Range End"]
    out["statistics"] = []

    current_events = get_current_events()

    cnx = get_database_connection()
    cursor = cnx.cursor()

    log.info("Read results")
    for current_event in current_events:

        competitors = []

        event = current_event.event_id
        log.info("Event = %s" % current_event.name)

        cursor.execute(query % current_event.event_id)

        for wca_id, person_name, country_id, v1, v2, v3, v4, v5 in cursor:

            competitor = Competitor(wca_id, person_name, country_id)

            i = bisect.bisect_left(competitors, competitor)
            if i == len(competitors) or competitors[i] != competitor:
                competitors.insert(i, competitor)
            competitor = competitors[i]

            for x in [v1, v2, v3, v4, v5]:
                if x < 1:
                    continue
                if event == "333mbf":
                    x = str(x)
                    points = get_mbld_points(x)[0]

                    j = bisect.bisect_left(competitor.results, points)
                    if j == len(competitor.results) or competitor.results[j] != points:
                        competitor.results.insert(j, points)
                else:
                    j = bisect.bisect_left(competitor.results, x)
                    if j == len(competitor.results) or competitor.results[j] != x:
                        competitor.results.insert(j, x)
        log.info("Found %s competitors" % len(competitors))

        log.info("Organize ranges")
        for competitor in competitors:
            # skipping people with only 1 result
            range_size, range_start, range_end = largest_range(competitor.results)

            if range_size == 1:
                continue

            competitor.range = range_size
            competitor.range_start = range_start
            competitor.range_end = range_end

        log.info("Sort results")
        competitors = sorted(
            filter(lambda c: c.range, competitors), key=lambda c: -c.range
        )
        log.info("Found %s elegible competitors" % len(competitors))

        table = []

        log.info("Compute table")
        prev = None
        count = 0
        for competitor in competitors:
            count += 1

            if count > LIMIT and prev != competitor.range:
                break

            if event in ["333fm", "333mbf"]:
                range_start = competitor.range_start
                range_end = competitor.range_end
            else:
                range_start = time_format(competitor.range_start)
                range_end = time_format(competitor.range_end)

            link = get_competitor_html_link(competitor.wca_id, competitor.name)
            table.append(
                [link, competitor.range, competitor.country, range_start, range_end]
            )

            prev = competitor.range

        explanation = (
            "Competitors that got all results from the range start to the range end in %s"
            % ("steps of 1" if event in ("333fm", "333mbf") else "steps of 0.01")
        )
        out["statistics"].append(
            {
                "keys": [current_event.name],
                "content": table,
                "headers": headers,
                "explanation": explanation,
                "showPositions": True,
                "positionTieBreakerIndex": 1,
            }
        )

    cnx.close()
    return out


@handle_statistics_control
def main():
    log.info(" ========== %s ==========" % title)
    out = ranges()

    create_statistics(out)


if __name__ == "__main__":
    main()
