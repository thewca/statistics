# python3 -m misc.python.best_ever_rank.best_ever_rank

import json
import time
from bisect import bisect_left, insort_left
from datetime import date, timedelta
from typing import List

from misc.python.util.database_util import get_database_connection
from misc.python.util.event_util import get_current_events
from misc.python.util.log_util import log

# WIP

query_dates = """select
    distinct c.start_date
from
    Competitions c
    inner join competition_events e on e.competition_id = c.id
where
    event_id = %(event_id)s
order by
    start_date"""

query_next_results = """select
    personId,
    ct.name,
    countryId,
    single,
    average,
    (
        select
            competitionId
        from
            Results results
            inner join Competitions competitions on results.competitionId = competitions.id
        where
            results.eventId = %(event_id)s
            and results.personId = today.personId
            and competitions.start_date = %(date)s
            and (
                results.best = today.single
                or results.average = today.average
            )
        limit
            1
    ) competition
from
    (
        select
            personId,
            r.countryId,
            min(
                case
                    when best > 0 then best
                    else null
                end
            ) single,
            min(
                case
                    when average > 0 then average
                    else null
                end
            ) average
        from
            Results r
            inner join Competitions c on r.competitionId = c.id
        where
            eventId = %(event_id)s
            and c.start_date = %(date)s
        group by
            r.personId,
            countryId
        order by
            personId
    ) today
    inner join Countries c on today.countryId = c.id
    inner join Continents ct on c.continentId = ct.id
where
    single is not null"""

insert_query = """insert into
    best_ever_ranks (
        person_id,
        continent,
        country_id,
        best_ever_rank,
        last_modified
    )
values
    (
        %(wca_id)s,
        %(continent)s,
        %(country_id)s,
        %(best_ever_rank)s,
        %(last_modified)s
    ) on duplicate key
update
    best_ever_rank = %(best_ever_rank)s,
    last_modified = %(last_modified)s"""


class Region:
    def __init__(self, name) -> None:
        self.name = name
        self.singles = []
        self.averages = []
        self.competitors = []

    def __eq__(self, o: object) -> bool:
        return self.name == o.name

    def __lt__(self, o):
        return self.name < o.name


class Result:
    def __init__(self, result, competition, date) -> None:
        self.result = result
        self.competition = competition
        self.start = date
        self.end = None
        self.rank = None

    def __repr__(self) -> str:
        return str(self.__dict__)

    def jsonable(self):
        return self.__dict__


class Results:
    def __init__(self, result, competition, date) -> None:
        self.current = Result(result, competition, date)
        self.best_rank = Result(None, None, None)

    def jsonable(self):
        return self.__dict__


class CompetitorWorld:
    def __init__(self, competitor_country) -> None:
        self.wca_id = competitor_country.wca_id
        self.single = Results(competitor_country.single.current.result,
                              competitor_country.single.current.competition, competitor_country.single.current.start)
        self.average = Results(competitor_country.average.current.result,
                               competitor_country.average.current.competition, competitor_country.average.current.start)

    def __eq__(self, o: object) -> bool:
        return self.wca_id == o.wca_id

    def __lt__(self, o):
        return self.wca_id < o.wca_id

    def jsonable(self):
        return self.__dict__

    def __repr__(self) -> str:
        return json.dumps(self.__dict__, indent=2, default=ComplexHandler)


class CompetitorContinent(CompetitorWorld):
    def __init__(self, competitor_country) -> None:
        super().__init__(competitor_country)
        self.continent = competitor_country.continent

    def __eq__(self, o: object) -> bool:
        return super().__eq__(o) and self.continent == o.continent

    def __lt__(self, o):
        if self.wca_id != o.wca_id:
            return self.wca_id < o.wca_id
        return self.continent < o.continent


class CompetitorCountry(CompetitorContinent):
    def __init__(self, wca_id, continent, country, single, average, competition, date) -> None:
        self.wca_id = wca_id
        self.continent = continent
        self.country = country
        self.single = Results(single, competition, date)
        self.average = Results(average, competition, date)

    def __eq__(self, o: object) -> bool:
        return super().__eq__(o) and self.country == o.country

    def __lt__(self, o):
        if self.wca_id != o.wca_id:
            return self.wca_id < o.wca_id
        if self.continent != o.continent:
            return self.continent < o.continent
        return self.country < o.country


def maybe_insort_and_return_region(regions, region_name):
    region = Region(region_name)

    index = bisect_left(regions, region)
    if index == len(regions) or regions[index] != region:
        regions.insert(index, region)

    return regions[index]


def update_singles(regions, competitor_region_name, old_single, new_single):
    region = regions[bisect_left(regions, Region(competitor_region_name))]

    old_index = bisect_left(region.all_time_singles, old_single)
    del region.all_time_singles[old_index]

    insort_left(region.all_time_singles, new_single)


def update_averages(regions, competitor_region_name, old_average, new_average):
    region = regions[bisect_left(regions, Region(competitor_region_name))]

    if old_average:
        old_index = bisect_left(region.all_time_averages, old_average)
        del region.all_time_averages[old_index]

    insort_left(region.all_time_averages, new_average)


def analyze_rank(region_results, competitor_result: Results, today):
    current_rank = bisect_left(
        region_results, competitor_result.current.result)
    competitor_result.current.rank = current_rank
    if competitor_result.best_rank.rank == None or current_rank < competitor_result.best_rank.rank:
        competitor_result.best_rank.result = competitor_result.current.result
        competitor_result.best_rank.competition = competitor_result.current.competition
        competitor_result.best_rank.start = competitor_result.current.start
        competitor_result.best_rank.rank = competitor_result.current.rank
        competitor_result.best_rank.end = None
    elif current_rank > competitor_result.best_rank.rank and competitor_result.best_rank.end == None:
        competitor_result.best_rank.end = today - timedelta(days=1)


def find_ranks(region: Region, today):
    for competitor in region.competitors:
        analyze_rank(region.singles, competitor.single, today)

        if competitor.average.current.result:
            analyze_rank(region.averages, competitor.average, today)


def find_or_create_competitor(region: Region, competitor):
    competitors = region.competitors
    index = bisect_left(competitors, competitor)
    if index == len(competitors) or competitors[index] != competitor:
        competitors.insert(index, competitor)
        insort_left(region.singles, competitor.single.current.result)

        if competitor.average.current.result:
            insort_left(region.averages, competitor.average.current.result)

    return competitors[index]


def update_results(region: Region, today_competitor, today):
    region_competitor = find_or_create_competitor(region, today_competitor)

    # Singles are always non null
    if today_competitor.single.current.result < region_competitor.single.current.result:
        # We remove the old result and insert the new one
        index = bisect_left(
            region.singles, region_competitor.single.current.result)
        del region.singles[index]

        insort_left(region.singles, today_competitor.single.current.result)

        region_competitor.single = today_competitor.single

    if today_competitor.average.current.result:
        if not region_competitor.average.current.result or today_competitor.average.current.result < region_competitor.average.current.result:
            if region_competitor.average.current.result:
                index = bisect_left(
                    region.averages, region_competitor.average.current.result)
                del region.averages[index]

            insort_left(region.averages,
                        today_competitor.average.current.result)

            region_competitor.average = today_competitor.average


def summarize_results(today_competitors, worlds, continents, countries, today):
    world = worlds[0]

    # Assign today's best result
    for today_competitor in today_competitors:

        competitor_world = CompetitorWorld(today_competitor)
        update_results(world, competitor_world, today)

        continent = maybe_insort_and_return_region(
            continents, today_competitor.continent)
        competitor_continent = CompetitorContinent(today_competitor)
        update_results(continent, competitor_continent, today)

        country = maybe_insort_and_return_region(
            countries, today_competitor.country)
        update_results(country, today_competitor, today)

    find_ranks(world, today)
    for continent in continents:
        find_ranks(continent, today)
    for country in countries:
        find_ranks(country, today)


def get_ranks_by_event(competitors, event_id, cursor):
    log.info(event_id)

    worlds = [Region("world")]
    continents = []
    countries = []

    log.info("Read results")
    cursor.execute(query_dates, {"event_id": event_id})
    dates = cursor.fetchall()
    log.info("Found %s dates" % len(dates))

    start = time.time()

    year = None
    for row in dates:
        current_date = row[0]
        if year != current_date.year:
            year = current_date.year
            log.info("Year: %s, %.2f" % (year, time.time()-start))

            # if year == 2019:  # TODO remove mock
            #     break

        today_competitors = []

        cursor.execute(query_next_results, {
                       "date": current_date, "event_id": event_id})
        today_results = cursor.fetchall()
        for wca_id, continent, country, single, average, competition in today_results:
            competitor = CompetitorCountry(
                wca_id, continent, country, single, average, competition, current_date)

            today_competitors.append(competitor)

        # One last summarization for the last day
        summarize_results(today_competitors,
                          worlds, continents, countries, current_date)

    return worlds, continents, countries


# TODO remove mock
class Ev:
    def __init__(self, event_id) -> None:
        self.event_id = event_id


def ComplexHandler(Obj):
    if hasattr(Obj, 'jsonable'):
        return Obj.jsonable()
    else:
        return str(Obj)


def main():
    # current_events = get_current_events()

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    competitors = []

    # current_events = get_current_events()
    # current_events = [Ev("333fm"), Ev("555bf")]
    current_events = [Ev("333fm")]

    for current_event in current_events:
        event_id = current_event.event_id
        worlds, continents, countries = get_ranks_by_event(
            competitors, event_id, cursor)

        print("="*10, "World", "="*10)
        for competitor in worlds[0].competitors:
            if competitor.wca_id == '2015CAMP17':
                print(competitor)

        print("="*10, "Continent", "="*10)
        for continent in continents:
            for competitor in continent.competitors:
                if competitor.wca_id == '2015CAMP17':
                    print(competitor)

        print("="*10, "Country", "="*10)
        for country in countries:
            for competitor in country.competitors:
                if competitor.wca_id == '2015CAMP17':
                    print(competitor)

            # competitor
            # if index == len(all_events_competitors) or all_events_competitors[index] != competitor:
            #     all_events_competitors.insert(index, competitor)

            # competitor = all_events_competitors[index]
            # competitor.insert_result(event_id, competitor_for_event)

    # log.info("Insert %s competitors into the database" %
    #          len(all_events_competitors))
    # today = date.today()
    # for competitor in all_events_competitors:
        # if competitor.wca_id == '2015CAMP17':
        #     print(json.dumps(competitor, default=ComplexHandler))
        #     break
        # best_ever_rank = json.dumps(
        #     competitor.all_results, default=ComplexHandler)
        # cursor.execute(insert_query, {"wca_id": competitor.wca_id, "continent": competitor.continent, "country_id": competitor.country_id,
        #                               "best_ever_rank": best_ever_rank, "last_modified": today})

    cnx.commit()

    cnx.close()


if __name__ == "__main__":
    main()
