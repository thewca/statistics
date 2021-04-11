# python3 -m misc.python.best_ever_rank.best_ever_rank

import csv
from bisect import bisect_left, insort_left
from datetime import datetime

# column order
# ['competitionId', 'eventId', 'roundTypeId', 'pos', 'best', 'average', 'personName', 'personId', 'personCountryId', 'formatId', 'value1', 'value2', 'value3', 'value4', 'value5', 'regionalSingleRecord', 'regionalAverageRecord', 'year', 'month', 'day']


def summarize_results(today_wca_ids, today_corresponding_best, all_time_wca_id, all_time_best_results, all_time_best_rank, all_time_bests):
    # Assign today's best result
    for wca_id, best in zip(today_wca_ids, today_corresponding_best):
        if not best:
            continue

        index = bisect_left(all_time_wca_id, wca_id)

        if index == len(all_time_wca_id) or all_time_wca_id[index] != wca_id:
            # Person is competing for the first time
            all_time_wca_id.insert(index, wca_id)
            all_time_best_results.insert(index, None)
            all_time_best_rank.insert(index, None)

        old_best = all_time_best_results[index]
        if not old_best:
            all_time_best_results[index] = best
            insort_left(all_time_bests, best)
        elif best < old_best:
            # In this case, competitor broke a PR
            # We can remove 1 result from the old an include a new best

            old_index = bisect_left(all_time_bests, old_best)
            del all_time_bests[old_index]

            insort_left(all_time_bests, best)

            all_time_best_results[index] = best

    for i in range(len(all_time_wca_id)):
        wca_id = all_time_wca_id[i]
        competitor_best = all_time_bests[i]

        if not competitor_best:
            continue

        current_best_rank = bisect_left(all_time_bests, competitor_best)
        if not all_time_best_rank[i] or current_best_rank < all_time_best_rank[i]:
            all_time_best_rank[i] = current_best_rank


def main():
    with open('WCA_export/WCA_export_Results_Ordered.tsv') as tsvin:
        tsvin = csv.reader(tsvin, delimiter='\t')

        all_time_bests = []

        all_time_wca_id = []
        all_time_best_results = []
        all_time_best_rank = []

        # Sorted by wca_id
        today_wca_ids = []
        today_corresponding_best = []

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

                summarize_results(today_wca_ids, today_corresponding_best,
                                  all_time_wca_id, all_time_best_results, all_time_best_rank, all_time_bests)
                today_wca_ids = []
                today_corresponding_best = []

            wca_id = line[7]

            i = bisect_left(today_wca_ids, wca_id)
            if i == len(today_wca_ids) or today_wca_ids[i] != wca_id:
                today_wca_ids.insert(i, wca_id)
                today_corresponding_best.insert(i, None)

            best = int(line[5])  # average because I know my best rank
            old_best = today_corresponding_best[i]
            if best <= 0:
                continue
            if not old_best or best < old_best:
                # In this case, we should removeth old result and insert the new one
                today_corresponding_best[i] = best

        c = 0
        for x, y, z in zip(all_time_wca_id, all_time_best_results, all_time_best_rank):
            if x == '2015CAMP17':
                print(x, y, z)
                break

        for i in range(100):
            print(all_time_best_results[i],
                  all_time_wca_id[i], all_time_best_rank[i])


if __name__ == "__main__":
    main()
