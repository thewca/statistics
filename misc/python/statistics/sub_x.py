# python3 -m misc.python.statistics.sub_x


from bisect import bisect_left

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.html_util import get_competitor_html_link
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import (
    create_statistics,
    handle_statistics_control,
)
from misc.python.util.time_util import time_format

title = "Most Sub-X solves"

RANGE = 6


query_wr = """select
	best
from
	RanksSingle rs
where
	eventId = '%s'
	and worldRank = 1
limit 1"""

query_results = """select
	personId,
	personName,
	countryId,
    best,
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

query_custom = """select
    personName,
    sum(
        IF(
            value1 > 0
            and value1 < %(sub_x)s,
            1,
            0
        ) + IF(
            value2 > 0
            and value2 < %(sub_x)s,
            1,
            0
        ) + IF(
            value3 > 0
            and value3 < %(sub_x)s,
            1,
            0
        ) + IF(
            value4 > 0
            and value4 < %(sub_x)s,
            1,
            0
        ) + IF(
            value5 > 0
            and value5 < %(sub_x)s,
            1,
            0
        )
    ) Solves
from
    Results r
where
    eventId = '%(event_id)s'
    and personId = ':WCA_ID'
group by
    personId,
    personName
"""


class Competitor(Comp):
    count = None

    def __init__(self, wca_id):
        super().__init__(wca_id)
        self.count = [0] * RANGE

    def __repr__(self) -> str:
        return super().__repr__()


def find_wr_single(cursor, event):
    log.info("Find WR single")

    cursor.execute(query_wr % event)

    result = cursor.fetchone()
    log.info("Result = %s" % result)

    return result[0]


def normalize_result(result, event) -> int:
    """WCA stores results in cents. 4 seconds is stored as 400.
    This normalizes the result so it can be used as index."""
    if event == "333fm":
        return result
    return result // 100


def can_be_discarded(result, wr_index, event) -> bool:
    return result < 1 or normalize_result(result, event) >= wr_index + RANGE


def sum_to_index(competitor, i):
    return sum(competitor.count[: i + 1])


def sub_x():

    LIMIT = 10

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    statistics = {}
    statistics["title"] = title
    statistics["groupName"] = "Results"
    statistics["statistics"] = []
    headers = ["Count", "Name", "Country"]
    statistics["displayMode"] = "GROUPED"

    for current_event in get_current_events():

        competitors = []

        event = current_event.event_id

        if event == "333mbf":
            continue

        log.info("Find sub x for %s" % current_event.name)

        wr_single = find_wr_single(cursor, event)
        wr_index = wr_single if event == "333fm" else wr_single // 100
        log.info("WR single for %s is %s" % (event, time_format(wr_single, event)))

        log.info(
            "Compute sub %s results" % (normalize_result(wr_single, event) + RANGE)
        )

        cursor.execute(query_results % event)
        for wca_id, person_name, country_id, best, v1, v2, v3, v4, v5 in cursor:

            # We exclude people with DNF or results out of the range
            if can_be_discarded(best, wr_index, event):
                continue

            competitor = Competitor(wca_id)

            i = bisect_left(competitors, competitor)
            if i == len(competitors) or competitors[i] != competitor:
                competitor.name = person_name
                competitor.country = country_id
                competitors.insert(i, competitor)
            competitor = competitors[i]

            for x in [v1, v2, v3, v4, v5]:
                if not can_be_discarded(x, wr_index, event):
                    index = normalize_result(x, event) - wr_index
                    competitor.count[index] += 1

        log.info("%s elegible competitors" % len(competitors))

        for i in range(RANGE):
            sorted_i = sorted(
                filter(lambda c: sum_to_index(c, i) > 0, competitors),
                key=lambda c: sum_to_index(c, i),
            )[::-1]
            c = 0
            prev = None
            stat = []
            for x in sorted_i:
                s = sum_to_index(x, i)

                # Ties
                if c >= LIMIT and s != prev:
                    break

                stat.append([s, get_competitor_html_link(x.wca_id, x.name), x.country])

                prev = s
                c += 1
            index = i + wr_index + 1
            current_sub = index if event == "333fm" else index * 100
            statistics["statistics"].append(
                {
                    "keys": [
                        current_event.name,
                        "Sub %s" % time_format(current_sub, event),
                    ],
                    "content": stat,
                    "headers": headers,
                    "showPositions": True,
                    "positionTieBreakerIndex": 0,
                    "sqlQueryCustom": query_custom
                    % {
                        "event_id": event,
                        "sub_x": (
                            (1 + i + wr_single // 100) * 100
                            if event != "333fm"
                            else (1 + i + wr_single)
                        ),
                    },
                }
            )

    cnx.close()
    return statistics


@handle_statistics_control
def main():
    log.info("========== %s ==========" % title)
    statistics = sub_x()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
