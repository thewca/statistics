# python3 -m misc.python.best_ever_rank.best_ever_rank

from bisect import bisect_left, insort_left
from datetime import date, timedelta
import json

from misc.python.model.competitor import Competitor as Comp
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


class Result:
    def __init__(self, result, start, competition) -> None:
        self.result = result
        self.start = start
        self.competition = competition
        self.end = None
        self.rank = None

    def __repr__(self) -> str:
        return str(self.__dict__)

    def jsonable(self):
        return self.__dict__


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
        return str(self.__dict__)

    def jsonable(self):
        return self.__dict__


class AllEventsCompetitor(Comp):
    def __init__(self, wca_id):
        super().__init__(wca_id)
        self.all_results = []

    def insert_result(self, event, competitor):
        d = competitor.__dict__
        self.wca_id = d.pop("wca_id")
        self.continent = d.pop("continent")
        self.country_id = d.pop("country")
        d["event_id"] = event
        self.all_results.append(d)

    def __repr__(self) -> str:
        return str(self.__dict__)

    def jsonable(self):
        return self.__dict__


class Region:
    def __init__(self, name) -> None:
        self.name = name
        self.all_time_singles = []
        self.all_time_averages = []

    def __eq__(self, o: object) -> bool:
        return self.name == o.name

    def __lt__(self, o):
        return self.name < o.name

    def __repr__(self) -> str:
        return str(self.__dict__)

    def jsonable(self):
        return self.__dict__


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


def get_ranks_by_event(event_id, cursor):
    log.info(event_id)

    worlds = [Region("world")]

    all_time_competitors = []
    continents = []
    countries = []

    # Sorted by wca_id
    today_competitors = []

    log.info("Read results")
    cursor.execute(query_dates, {"event_id": event_id})
    dates = cursor.fetchall()
    log.info("Found %s dates" % len(dates))
    for row in dates:
        current_date = row[0]
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

    return all_time_competitors


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

    all_events_competitors = []

#    current_events = get_current_events()
    # current_events = [Ev("333fm"), Ev("555bf")]
    current_events = [Ev("555bf")]

    for current_event in current_events:
        event_id = current_event.event_id
        competitors = get_ranks_by_event(event_id, cursor)

        for competitor_for_event in competitors:

            competitor = AllEventsCompetitor(competitor_for_event.wca_id)
            index = bisect_left(all_events_competitors, competitor)
            if index == len(all_events_competitors) or all_events_competitors[index] != competitor:
                all_events_competitors.insert(index, competitor)

            competitor = all_events_competitors[index]
            competitor.insert_result(event_id, competitor_for_event)

    log.info("Insert %s competitors into the database" %
             len(all_events_competitors))
    today = date.today()
    for competitor in all_events_competitors:
        # if competitor.wca_id == '2015CAMP17':
        #     print(json.dumps(competitor, default=ComplexHandler))
        #     break
        best_ever_rank = json.dumps(
            competitor.all_results, default=ComplexHandler)
        cursor.execute(insert_query, {"wca_id": competitor.wca_id, "continent": competitor.continent, "country_id": competitor.country_id,
                                      "best_ever_rank": best_ever_rank, "last_modified": today})

    cnx.commit()

    cnx.close()


if __name__ == "__main__":
    main()
