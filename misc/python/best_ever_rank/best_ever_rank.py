# python3 -m misc.python.best_ever_rank.best_ever_rank

from bisect import bisect_left, insort_left
from datetime import date, timedelta

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
        return "result=%s, rank=%s, start=%s, competition=%s, end=%s" % (self.result, self.rank, self.start, self.competition, self.end)


class Competitor(Comp):
    def __init__(self, wca_id, single, average, start, competition):
        super().__init__(wca_id)
        self.single = Result(single, start, competition)
        self.average = Result(average, start, competition)

    def __repr__(self) -> str:
        attrs = vars(self)
        return ', '.join("%s: %s" % item for item in attrs.items())


def summarize_results(today, today_competitors, all_time_competitors, all_time_singles, all_time_averages):
    # Assign today's best result
    for competitor in today_competitors:
        index = bisect_left(all_time_competitors, competitor)
        if index == len(all_time_competitors) or all_time_competitors[index] != competitor:
            # Person is competing for the first time
            all_time_competitors.insert(index, competitor)

            insort_left(all_time_singles, competitor.single.result)
            if competitor.average.result:
                insort_left(all_time_averages, competitor.average.result)

        # old_single is always defined
        all_time_competitor = all_time_competitors[index]
        old_single = all_time_competitor.single.result
        if competitor.single.result < old_single:
            # In this case, competitor broke a PR
            # We can remove 1 result from the old an include a new best

            old_index = bisect_left(all_time_singles, old_single)
            del all_time_singles[old_index]

            insort_left(all_time_singles, competitor.single.result)

            all_time_competitor.single.result = competitor.single.result
            all_time_competitor.single.competition = competitor.single.competition
            all_time_competitor.single.start = today
            all_time_competitor.single.end = None

        if competitor.average.result:

            old_average = all_time_competitor.average.result
            if not old_average or competitor.average.result < old_average:
                # In this case, competitor broke a PR
                # We can remove 1 result from the old an include a new best

                if old_average:
                    old_index = bisect_left(all_time_averages, old_average)
                    del all_time_averages[old_index]

                insort_left(all_time_averages, competitor.average.result)

                all_time_competitor.average.result = competitor.average.result
                all_time_competitor.average.competition = competitor.average.competition
                all_time_competitor.average.start = competitor.average.start
                all_time_competitor.average.end = None

    for competitor in all_time_competitors:
        current_best_rank = bisect_left(
            all_time_singles, competitor.single.result)
        if competitor.single.rank == None or current_best_rank < competitor.single.rank:
            competitor.single.rank = current_best_rank
            competitor.single.result = competitor.single.result
            competitor.single.end = None
        elif current_best_rank > competitor.single.rank and competitor.single.end == None:
            competitor.single.end = today - timedelta(days=1)

        if competitor.average.result:
            current_best_rank = bisect_left(
                all_time_averages, competitor.average.result)
            if competitor.average.rank == None or current_best_rank < competitor.average.rank:
                competitor.average.rank = current_best_rank
                competitor.average.result = competitor.average.result
                competitor.average.end = None
            elif current_best_rank > competitor.average.rank and competitor.average.end == None:
                competitor.average.end = today - timedelta(days=1)


def main():

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    all_time_singles = []
    all_time_averages = []

    # Sorted by wca_id
    all_time_competitors = []
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
        for wca_id, single, average, competition_id in today_results:
            competitor = Competitor(
                wca_id, single, average, current_date, competition_id)
            today_competitors.append(competitor)

        # One last summarization for the last day
        summarize_results(current_date, today_competitors,
                          all_time_competitors, all_time_singles, all_time_averages)

    for competitor in all_time_competitors:
        if competitor.wca_id == '2015CAMP17':
            print(competitor)
            break

    cnx.close()


if __name__ == "__main__":
    main()
