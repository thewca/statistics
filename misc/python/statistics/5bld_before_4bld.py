# python3 -m misc.python.statistics.5bld_before_4bld

import bisect

from misc.python.statistics.days_5bld_become_faster_than_4bld import Competitor
from misc.python.util.database_util import get_database_connection
from misc.python.util.html_util import (get_competition_html_link,
                                        get_competitor_html_link)
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics
from misc.python.util.time_util import time_format

title = "Competitors who got 5BLD before 4BLD"

query = """select
	personId,
	personName,
	r.countryId,
    eventId,
	best,
	c.id,
	c.name,
	c.start_date
from
	Results r
inner join Competitions c on
	r.competitionId = c.id
where
	eventId in ('%s', '%s')
	and best > 0
order by
	c.start_date"""

# TODO this query is very expensive, we are not shipping it until we can restrict queries to logged users so we can prevent multiple runs
query_custom = """select
    name,
    id,
    days
from
    (
        select
            name,
            id,
            datediff(
                (
                    select
                        min(start_date)
                    from
                        Results r
                        inner join Competitions c on r.competitionId = c.id
                    where
                        r.personId = p.id
                        and eventId = ':SECOND_EVENT_ID'
                        and best > 0
                ),
                (
                    select
                        min(start_date)
                    from
                        Results r
                        inner join Competitions c on r.competitionId = c.id
                    where
                        r.personId = p.id
                        and eventId = ':FIRST_EVENT_ID'
                        and best > 0
                )
            ) days
        from
            Persons p
    ) result
where
    days > 0
order by
    days"""


def compare_results(ev1, ev2):
    """Checks if a competitor got ev1 before ev2, expect for 333mbf and 333mbo, due to time encode."""

    competitors = []

    log.info("Connect to the database")
    cnx = get_database_connection()
    cursor = cnx.cursor()
    cursor.execute(query % (ev1, ev2))

    evs = [ev1, ev2]

    log.info("Assign results")
    for wca_id, name, country_id, event_id, best, competition_id, competition_name, date in cursor:
        competitor = Competitor(wca_id, country_id, name)

        i = bisect.bisect_left(competitors, competitor)
        if i == len(competitors) or competitors[i] != competitor:
            competitor.first_results = [None, None]
            competitor.first_competitions = [None, None]
            competitor.first_dates = [None, None]
            competitor.diff = None

            competitors.insert(i, competitor)

        competitor = competitors[i]

        j = evs.index(event_id)
        if competitor.first_results[j] == None:
            competitor.first_results[j] = time_format(best)
            competitor.first_competitions[j] = [
                competition_id, competition_name]
            competitor.first_dates[j] = date
    log.info("Found %s competitors" % len(competitors))

    competitors = list(filter(
        lambda c: c.first_results[0] and c.first_results[1], competitors))
    log.info("Found %s competitors with both success" % len(competitors))

    log.info("Fill diffs")
    for competitor in competitors:
        competitor.diff = (
            competitor.first_dates[1]-competitor.first_dates[0]).days

    # Filter
    competitors = sorted(
        filter(lambda c: c.diff > 0, competitors), key=lambda c: -c.diff)
    log.info("Found %s competitors that got %s before %s" %
             (len(competitors), ev1, ev2))

    table = []
    for competitor in competitors:

        link = get_competitor_html_link(competitor.wca_id, competitor.name)
        table.append([competitor.diff, link, competitor.first_results[0], get_competition_html_link(
            competitor.first_competitions[0][0], competitor.first_competitions[0][1]), competitor.first_results[1], get_competition_html_link(
            competitor.first_competitions[1][0], competitor.first_competitions[1][1])])

    headers = ["Days", "Name", "First result %s" %
               ev1, "Competition", "First result %s" % ev2, "Competition"]
    out = {}
    out["title"] = title
    out["groupName"] = "Events"
    out["displayMode"] = "DEFAULT"
    out["explanation"] = "In case of multiple first results (eg. ao3), best one is taken."
    out["statistics"] = [{"keys": [], "content": table,
                          "headers": headers, "showPositions": True, "positionTieBreakerIndex": 0}]

    cnx.close()

    return out


def main():
    log.info(" ========== %s ==========" % title)

    ev1 = "555bf"
    ev2 = "444bf"

    data = compare_results(ev1, ev2)

    create_statistics(data)


if __name__ == "__main__":
    main()
