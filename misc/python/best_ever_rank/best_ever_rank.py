# python3 -m misc.python.best_ever_rank.best_ever_rank

import csv
from bisect import bisect_left, insort_left
from datetime import datetime

# column order
# ['competitionId', 'eventId', 'roundTypeId', 'pos', 'best', 'average', 'personName', 'personId', 'personCountryId', 'formatId', 'value1', 'value2', 'value3', 'value4', 'value5', 'regionalSingleRecord', 'regionalAverageRecord', 'year', 'month', 'day']


class Competitor:
    wca_id = None
    best = None
    best_rank = None
    best_rank_start = None

    def __init__(self, wca_id):
        self.wca_id = wca_id

    def __lt__(self, other):
        return self.wca_id < other.wca_id

    def __eq__(self, other):
        return self.wca_id == other.wca_id

    def __repr__(self):
        return "Competitor[wca_id=%s, best=%s, best_rank=%s]" % (self.wca_id, self.best, self.best_rank)


def summarize_results(today_competitors, all_time_competitors, all_time_bests):
    # Assign today's best result
    for competitor in today_competitors:
        if not competitor.best:
            continue

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

            all_time_competitors[index].best = competitor.best

    for competitor in all_time_competitors:
        if not competitor.best:
            continue

        current_best_rank = bisect_left(all_time_bests, competitor.best)
        if not competitor.best_rank or current_best_rank < competitor.best_rank:
            competitor.best_rank = current_best_rank


def main():
    with open('WCA_export/WCA_export_Results_Ordered.tsv') as tsvin:
        tsvin = csv.reader(tsvin, delimiter='\t')

        all_time_bests = []

        # Sorted by wca_id
        all_time_competitors = []
        today_competitors = []

        # Skip header
        next(tsvin, None)

        for line in tsvin:
            y, m, d = map(int, [line[17], line[18], line[19]])
            break

        current_date = datetime(y, m, d)
        for line in tsvin:
            event = line[1]
            if event != '333fm':
                continue

            y, m, d = map(int, [line[17], line[18], line[19]])
            this_date = datetime(y, m, d)
            if current_date != this_date:
                # Compute rankings after today

                summarize_results(today_competitors,
                                  all_time_competitors, all_time_bests)
                today_competitors = []

            wca_id = line[7]
            competitor = Competitor(wca_id)

            i = bisect_left(today_competitors, competitor)
            if i == len(today_competitors) or today_competitors[i] != competitor:
                today_competitors.insert(i, competitor)

            competitor = today_competitors[i]
            best = int(line[5])  # average because I know my best rank
            old_best = competitor.best
            if best <= 0:
                continue
            if not old_best or best < old_best:
                # In this case, we should removeth old result and insert the new one
                competitor.best = best

        summarize_results(today_competitors,
                          all_time_competitors, all_time_bests)

        c = 0
        for competitor in all_time_competitors:
            if competitor.wca_id == '2015CAMP17':
                print(competitor)
                break


if __name__ == "__main__":
    main()
