# python3 -m misc.python.best_ever_rank.best_ever_rank

from bisect import bisect_left, insort_left
from datetime import date, datetime, timedelta

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


class Competitor(Comp):
    def __init__(self, wca_id, single, average, competition_id):
        super().__init__(wca_id)
        self.single = single
        self.average = average
        self.competition_id = competition_id

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

            all_time_competitors[index].best_single_rank = None
            all_time_competitors[index].best_average_rank = None

            insort_left(all_time_singles, competitor.single)
            if competitor.average:
                insort_left(all_time_averages, competitor.average)

        # old_single is always defined
        old_single = all_time_competitors[index].single
        if competitor.single < old_single:
            # In this case, competitor broke a PR
            # We can remove 1 result from the old an include a new best

            old_index = bisect_left(all_time_singles, old_single)
            del all_time_singles[old_index]

            insort_left(all_time_singles, competitor.single)

            all_time_competitors[index].single = competitor.single
            all_time_competitors[index].single_competition_id = competitor.competition_id
            all_time_competitors[index].single_min_date = today
            all_time_competitors[index].single_rank_start = today
            all_time_competitors[index].single_rank_end = None

        if competitor.average:

            old_average = all_time_competitors[index].average
            if not old_average or competitor.average < old_average:
                # In this case, competitor broke a PR
                # We can remove 1 result from the old an include a new best

                if old_average:
                    old_index = bisect_left(all_time_averages, old_average)
                    del all_time_averages[old_index]

                insort_left(all_time_averages, competitor.average)

                all_time_competitors[index].average = competitor.average
                all_time_competitors[index].average_competition_id = competitor.competition_id
                all_time_competitors[index].average_min_date = today
                all_time_competitors[index].average_rank_start = today
                all_time_competitors[index].average_rank_end = None

    for competitor in all_time_competitors:
        current_best_rank = bisect_left(all_time_singles, competitor.single)
        if competitor.best_single_rank == None or current_best_rank < competitor.best_single_rank:
            competitor.best_single_rank = current_best_rank
            competitor.best_rank_single = competitor.single
            competitor.best_rank_single_end = None

        if competitor.average:
            current_best_rank = bisect_left(
                all_time_averages, competitor.average)
            if competitor.best_average_rank == None or current_best_rank < competitor.best_average_rank:
                competitor.best_average_rank = current_best_rank
                competitor.best_rank_average = competitor.average
                competitor.best_rank_average_end = None


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
            competitor = Competitor(wca_id, single, average, competition_id)
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
