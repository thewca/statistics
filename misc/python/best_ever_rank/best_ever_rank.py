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
    person_id,
    min_best best,
    (
        select
            competitionId
        from
            Results r
            inner join Competitions c on r.competitionId = c.id
        where
            r.eventId = '%(event_id)s'
            and r.%(result_type)s = min_best
            and r.personId = personId
            and c.start_date = date('%(date)s')
        limit
            1
    ) competition_id
from
    (
        select
            personId person_id,
            min(average) min_best
        from
            Results r
            inner join Competitions c on r.competitionId = c.id
        where
            %(result_type)s > 0
            and eventId = '%(event_id)s'
            and c.start_date = date('%(date)s')
        group by
            personId
    ) min_results"""


class Competitor(Comp):
    def __init__(self, wca_id, best, competition_id, min_date):
        super().__init__(wca_id)
        self.best = best
        self.competition_id = competition_id
        self.min_date = min_date
        self.best_rank = None

    def __repr__(self) -> str:
        return "Competitor[wca_id=%s, best=%s, competition_id=%s, min_date=%s, best_rank=%s]" % (self.wca_id, self.best, self.competition_id, self.min_date, self.best_rank)


def summarize_results(today, today_competitors, all_time_competitors, all_time_bests):
    # Assign today's best result
    for competitor in today_competitors:
        index = bisect_left(all_time_competitors, competitor)
        if index == len(all_time_competitors) or all_time_competitors[index] != competitor:
            # Person is competing for the first time
            all_time_competitors.insert(index, competitor)
            insort_left(all_time_bests, competitor.best)

        old_best = all_time_competitors[index].best
        if not old_best:
            all_time_competitors[index].best = competitor.best
            insort_left(all_time_bests, competitor.best)
        elif competitor.best < old_best:
            # In this case, competitor broke a PR
            # We can remove 1 result from the old an include a new best

            old_index = bisect_left(all_time_bests, old_best)
            del all_time_bests[old_index]

            insort_left(all_time_bests, competitor.best)

            all_time_competitors[index].competition_id = competitor.competition_id
            all_time_competitors[index].min_date = today
            all_time_competitors[index].best_rank_start = today
            all_time_competitors[index].best = competitor.best
            all_time_competitors[index].best_rank_end = None

    for competitor in all_time_competitors:
        current_best_rank = bisect_left(all_time_bests, competitor.best)
        if not competitor.best_rank or current_best_rank < competitor.best_rank:
            competitor.best_rank = current_best_rank
            competitor.best_rank_best = competitor.best
            competitor.best_rank_end = None

        # if current_best_rank > competitor.best_rank and not competitor.best_rank_end:
        #     competitor.best_rank_end = today - timedelta(days=1)


def main():

    log.info("Get database connection")
    cnx = get_database_connection()
    cursor = cnx.cursor()

    all_time_bests = []

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
        cursor.execute(query_next_results % {
                       "result_type": "average", "date": current_date, "event_id": event_id})
        today_results = cursor.fetchall()
        for wca_id, best, competition_id in today_results:
            competitor = Competitor(wca_id, best, competition_id, current_date)
            today_competitors.append(competitor)

        # One last summarization for the last day
        summarize_results(current_date, today_competitors,
                          all_time_competitors, all_time_bests)

    for competitor in all_time_competitors:
        if competitor.wca_id == '2015CAMP17':
            print(competitor)
            break

    cnx.close()


if __name__ == "__main__":
    main()
