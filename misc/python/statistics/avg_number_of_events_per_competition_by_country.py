# python3 -m misc.python.statistics.avg_number_of_events_per_competition_by_country

from datetime import datetime

from misc.python.util.database_util import get_database_connection
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics

current_year = datetime.now().year
range = 5

min_year = current_year - range
max_year = current_year - 1

title = "Average number of events in a competition for each country from %s to %s" % (
    min_year, max_year)

query = """select
	format(average, 2),
	name
from
	(
	select
		ct.name,
		count(*)/ count(distinct competition_id) average
	from
		Competitions c
	inner join competition_events e on
		c.id = e.competition_id
	inner join Countries ct on
		c.countryId = ct.id
	where
		year(c.start_date) <= %(max_year)s
		and year(c.start_date) >= %(min_year)s
	group by
		countryId) result
order by
	average desc,
	name
"""


def avg_events():

    cnx = get_database_connection()
    cursor = cnx.cursor()
    cursor.execute(query, {"min_year": min_year, "max_year": max_year})
    content = cursor.fetchall()

    out = {}
    out["title"] = title
    out["group"] = "Events"
    out["displayMode"] = "DEFAULT"
    headers = ["Avg", "Country"]
    out["statistics"] = [
        {"keys": [], "content": content, "headers": headers, "showPositions": True, "positionTieBreakerIndex": 0}]

    cnx.close()

    return out


def main():
    log.info(" ========== %s ==========" % title)

    stat = avg_events()
    create_statistics(stat)


if __name__ == "__main__":
    main()
