# python3 -m misc.python.best_ever_rank.best_ever_rank

from bisect import bisect_left, insort_left
from datetime import date, timedelta
from typing import List, Text

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.log_util import log

# WIP

query_date = """select
    min(start_date)
from
    Competitions c
    inner join competition_events e on e.competition_id = c.id
where
    start_date > %(date)s
    and event_id = %(event_id)s"""

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
            personId
    ) today
    inner join Persons p on today.personId = p.id
    inner join Countries c on p.countryId = c.id
    inner join Continents ct on c.continentId = ct.id
where
    single is not null"""


class Result:
    def __init__(self, result, start, competition) -> None:
        self.result = result
        self.start = start
        self.competition = competition
        self.end = None
        self.rank = None

    def __repr__(self) -> str:
        return "{result=%s, rank=%s, start=%s, competition=%s, end=%s}" % (self.result, self.rank, self.start, self.competition, self.end)


class Competitor(Comp):
    world = "world"

    def __init__(self, wca_id, continent, country, single, average, start, competition):
        super().__init__(wca_id)
        self.single = Result(single, start, competition)
        self.average = Result(average, start, competition)
        self.best_world_single_rank = Result(single, start, competition)
        self.best_world_average_rank = Result(average, start, competition)
        self.best_continent_single_rank = Result(single, start, competition)
        self.best_continent_average_rank = Result(average, start, competition)
        self.best_country_single_rank = Result(single, start, competition)
        self.best_country_average_rank = Result(average, start, competition)
        self.continent = continent
        self.country = country

    def __repr__(self) -> str:
        attrs = vars(self)
        return '\n'.join("%s: %s" % item for item in attrs.items())


class Region:
    def __init__(self, name) -> None:
        self.name = name
        self.all_time_singles = []
        self.all_time_averages = []

    def __eq__(self, o: object) -> bool:
        return self.name == o.name

    def __lt__(self, o):
        return self.name < o.name


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


def analyze_best(regions, region_name, competitor_best_single_region, competitor_best_average_region, competitor_single, competitor_average, today):
    region = maybe_insort_and_return_region(regions, region_name)

    current_best_rank = bisect_left(
        region.all_time_singles, competitor_single.result)
    if competitor_best_single_region.rank == None or current_best_rank < competitor_best_single_region.rank:
        competitor_best_single_region.rank = current_best_rank
        competitor_best_single_region.result = competitor_single.result
        competitor_best_single_region.start = competitor_single.start
        competitor_best_single_region.competition = competitor_single.competition
        competitor_best_single_region.end = None
    elif current_best_rank > competitor_best_single_region.rank and competitor_best_single_region.end == None:
        competitor_best_single_region.end = today - timedelta(days=1)

    if competitor_average.result:
        current_best_rank = bisect_left(
            region.all_time_averages, competitor_average.result)
        if competitor_best_average_region.rank == None or current_best_rank < competitor_best_average_region.rank:
            competitor_best_average_region.rank = current_best_rank
            competitor_best_average_region.result = competitor_average.result
            competitor_best_average_region.start = competitor_average.start
            competitor_best_average_region.competition = competitor_average.competition
            competitor_best_average_region.end = None
        elif current_best_rank > competitor_best_average_region.rank and competitor_best_average_region.end == None:
            competitor_best_average_region.end = today - timedelta(days=1)


def find_ranks(all_time_competitors, worlds, continents, countries, today):
    for competitor in all_time_competitors:
        analyze_best(worlds, competitor.world, competitor.best_world_single_rank,
                     competitor.best_world_average_rank, competitor.single, competitor.average, today)
        analyze_best(continents, competitor.continent, competitor.best_continent_single_rank,
                     competitor.best_continent_average_rank, competitor.single, competitor.average, today)
        analyze_best(countries, competitor.country, competitor.best_country_single_rank,
                     competitor.best_country_average_rank, competitor.single, competitor.average, today)


def summarize_results(all_time_competitors, today_competitors, worlds, continents, countries, today):
    world = worlds[0]

    # Assign today's best result
    for competitor in today_competitors:
        index = bisect_left(all_time_competitors, competitor)
        if index == len(all_time_competitors) or all_time_competitors[index] != competitor:
            # Person is competing for the first time

            all_time_competitors.insert(index, competitor)

            continent = maybe_insort_and_return_region(
                continents, competitor.continent)
            country = maybe_insort_and_return_region(
                countries, competitor.country)

            insort_left(world.all_time_singles, competitor.single.result)
            insort_left(continent.all_time_singles, competitor.single.result)
            insort_left(country.all_time_singles, competitor.single.result)
            if competitor.average.result:
                insort_left(world.all_time_averages, competitor.average.result)
                insort_left(continent.all_time_averages,
                            competitor.average.result)
                insort_left(country.all_time_averages,
                            competitor.average.result)

        all_time_competitor = all_time_competitors[index]

        # old_single is always defined
        old_single = all_time_competitor.single.result
        new_single = competitor.single.result
        if competitor.single.result < old_single:
            # In this case, competitor broke a PR
            # We can remove 1 result from the old an include a new best

            update_singles(worlds, competitor.world, old_single, new_single)
            update_singles(continents, competitor.continent,
                           old_single, new_single)
            update_singles(countries, competitor.country,
                           old_single, new_single)

            all_time_competitor.single = competitor.single

        if competitor.average.result:

            old_average = all_time_competitor.average.result
            new_average = competitor.average.result
            if not old_average or new_average < old_average:
                # In this case, competitor broke a PR
                # We can remove 1 result from the old an include a new best

                update_averages(worlds, competitor.world,
                                old_average, new_average)
                update_averages(continents, competitor.continent,
                                old_average, new_average)
                update_averages(countries, competitor.country,
                                old_average, new_average)

                all_time_competitor.average = competitor.average

    find_ranks(all_time_competitors, worlds, continents, countries, today)


def main():
    all_time_competitors = []
    worlds = [Region("world")]
    continents = []
    countries = []

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    # Sorted by wca_id
    today_competitors = []

    event_id = '333fm'

    log.info("Read results")
    current_date = date(1970, 1, 1)
    while True:
        cursor.execute(
            query_date, {"date": current_date, "event_id": event_id})
        current_date = cursor.fetchone()[0]
        if not current_date:
            break
        cursor.execute(query_next_results, {
                       "date": current_date, "event_id": event_id})
        today_results = cursor.fetchall()
        for wca_id, continent, country, single, average, competition_id in today_results:
            competitor = Competitor(
                wca_id, continent, country, single, average, current_date, competition_id)
            today_competitors.append(competitor)

        # One last summarization for the last day
        summarize_results(all_time_competitors, today_competitors,
                          worlds, continents, countries, current_date)

    for competitor in all_time_competitors:
        if competitor.wca_id == '2015CAMP17':
            print(competitor)
            break

    cnx.close()


if __name__ == "__main__":
    main()
