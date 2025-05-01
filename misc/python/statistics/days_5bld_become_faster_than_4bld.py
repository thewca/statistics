# python3 -m misc.python.statistics.days_5bld_become_faster_than_4bld


import bisect

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.html_util import (
    get_competition_html_link,
    get_competitor_html_link,
)
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import (
    create_statistics,
    handle_statistics_control,
)
from misc.python.util.time_util import time_format

title = "5bld became faster than 4bld"


class Competitor(Comp):
    def __init__(self, wca_id, country_id, name):
        super().__init__(wca_id)
        self.country_id = country_id
        self.name = name

        self.first_results = [None, None]
        self.first_dates = [None, None]
        self.first_competitions = [None, None]
        self.diff = None


query = """select
    person_id,
    r.country_id,
    person_name,
    best,
    start_date,
    competition_id,
    c.name,
    event_id
from
    results r
    inner join competitions c on r.competition_id = c.id
where
    event_id in ('%s', '%s')
    and best > 0
order by
    c.start_date,
    round_type_id"""


def compare_results(ev1, ev2):
    """Checks if ev1 happened before ev2."""

    # This won't work for 333mbf and 333mbo, since `best` is encoded differently.

    competitors = []

    log.info("Get connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    cursor.execute(query % (ev1, ev2))

    for (
        wca_id,
        country_id,
        name,
        best,
        start_date,
        competition_id,
        competition_name,
        event_id,
    ) in cursor:
        competitor = Competitor(wca_id, country_id, name)
        i = bisect.bisect_left(competitors, competitor)
        if i == len(competitors) or competitors[i] != competitor:
            competitors.insert(i, competitor)
        competitor = competitors[i]

        # first ev1 result ever
        if event_id == ev1 and not competitor.first_results[0]:
            competitor.first_results[0] = best
            competitor.first_competitions[0] = [competition_id, competition_name]
            competitor.first_dates[0] = start_date
        elif (
            event_id == ev2
            and not competitor.first_results[1]
            and competitor.first_results[0]
            and best < competitor.first_results[0]
        ):
            competitor.first_results[1] = best
            competitor.first_competitions[1] = [competition_id, competition_name]

            competitor.first_dates[1] = start_date

            competitor.diff = (
                competitor.first_dates[1] - competitor.first_dates[0]
            ).days

    table = []

    sorted_competitors = sorted(
        filter(lambda c: c.diff and c.diff > 0, competitors), key=lambda c: c.diff
    )

    for competitor in sorted_competitors:

        table.append(
            [
                competitor.diff,
                get_competitor_html_link(competitor.wca_id, competitor.name),
                time_format(competitor.first_results[0]),
                get_competition_html_link(
                    competitor.first_competitions[0][0],
                    competitor.first_competitions[0][1],
                ),
                time_format(competitor.first_results[1]),
                get_competition_html_link(
                    competitor.first_competitions[1][0],
                    competitor.first_competitions[1][1],
                ),
            ]
        )

    out = {}
    out["explanation"] = (
        "In case of multiple first results (eg. ao3), best one is taken."
    )
    out["title"] = title
    out["displayMode"] = "DEFAULT"
    out["groupName"] = "Competitors"
    headers = [
        "Days",
        "Name",
        "First %s result" % ev1,
        "Competition",
        "First faster %s result" % ev2,
        "Competition",
    ]
    out["statistics"] = [
        {
            "keys": [],
            "content": table,
            "headers": headers,
            "showPositions": True,
            "positionTieBreakerIndex": 0,
        }
    ]
    return out


@handle_statistics_control
def main():
    log.info(" ========== %s ==========" % title)

    ev1 = "444bf"
    ev2 = "555bf"

    out = compare_results(ev1, ev2)

    create_statistics(out)


if __name__ == "__main__":
    main()
