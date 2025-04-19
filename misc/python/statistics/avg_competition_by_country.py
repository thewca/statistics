# python3 -m misc.python.statistics.avg_competition_by_country

# avg number of comps per country since 2010
# we are taking the average of the number of comps/year since 2010.

from datetime import datetime

from misc.python.util.database_util import get_database_connection
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import (
    create_statistics,
    handle_statistics_control,
)

period = 5
current_year = datetime.today().year
max_year = current_year - 1
min_year = current_year - period

title = "Average number of competitions per year by country since %s" % min_year

query = """select
    format(average, 2),
    name,
    m5,
    m4,
    m3,
    m2,
    m1
from
    (
        select
            c.name,
            (
                select
                    count(*) / 5
                from
                    Competitions c2
                where
                    countryId = c.id
                    and year(c2.start_date) >= %(min_year)s
                    and year(c2.start_date) <= %(max_year)s
                    and results_posted_at is not null
            ) average,%(sub_queries)s
        from
            Countries c
    ) result
where
    average > 0
order by
    average desc"""

sub_query = """
		(
		select
			count(*)
		from
			Competitions c2
		where
			c2.countryId = c.id
            and results_posted_at is not null
			and year(c2.start_date) = year(current_date())-%(diff)s) m%(diff)s"""


def avg_competitions():
    sub_queries = [sub_query % ({"diff": i + 1}) for i in range(period)]
    final_query = query % (
        {
            "min_year": min_year,
            "max_year": max_year,
            "sub_queries": ",".join(sub_queries),
        }
    )

    log.info("Connect to the database")
    cnx = get_database_connection()
    cursor = cnx.cursor(final_query)
    cursor.execute(final_query)

    table = cursor.fetchall()
    log.info("Found %s countries" % len(table))

    out = {}
    out["title"] = title
    out["groupName"] = "Countries"
    out["displayMode"] = "DEFAULT"
    headers = ["Avg", "Country", *range(min_year, max_year + 1)]

    out["statistics"] = [
        {
            "keys": [],
            "content": table,
            "headers": headers,
            "showPositions": True,
            "positionTieBreakerIndex": 0,
        }
    ]
    cnx.close()
    return out


@handle_statistics_control
def main():
    log.info(" ========== %s ==========" % title)

    stat = avg_competitions()
    create_statistics(stat)


if __name__ == "__main__":
    main()
