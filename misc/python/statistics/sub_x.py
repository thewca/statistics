# python3 -m misc.python.statistics.sub_x

import csv
from bisect import bisect_left

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.log_util import log

RANGE = 6


class Competitor(Comp):
    count = None

    def __init__(self, wca_id):
        super().__init__(wca_id)
        self.count = [0] * RANGE

    def __repr__(self) -> str:
        return super().__repr__()


def find_wr_single(event):
    log.info("Find WR single")

    tsv_file = open("WCA_export/WCA_export_Results.tsv")
    tsvreader = csv.reader(tsv_file, delimiter="\t")

    wr_single = None
    for line in tsvreader:
        this_event = line[1]
        if this_event != event:  # Also skips header
            continue
        best = int(line[4])
        if best < 1:
            continue
        if wr_single is None or best < wr_single:
            wr_single = best
    return wr_single


def normalize_result(result) -> int:
    """WCA stores results in cents. 4 seconds is stored as 400.
    This normalizes the result so it can be used as index."""
    return result // 100


def can_be_discarded(result, wr_index) -> bool:
    return result < 1 or normalize_result(result) >= wr_index + RANGE


def sum_to_index(competitor, i):
    return sum(competitor.count[:i+1])


def main():

    competitors = []

    event = "333"
    wr_single = find_wr_single(event)
    wr_index = wr_single // 100
    log.info("WR single for %s is %s" % (event, wr_single))

    tsv_file = open("WCA_export/WCA_export_Results.tsv")

    log.info("Compute sub %s results" % (normalize_result(wr_single)+RANGE))
    tsvreader = csv.reader(tsv_file, delimiter="\t")
    for line in tsvreader:
        this_event = line[1]
        if this_event != event:  # Also skips header
            continue

        best = int(line[4])

        # We exclude people with DNF or results out of the range
        if can_be_discarded(best, wr_index):
            continue

        wca_id = line[7]
        competitor = Competitor(wca_id)

        i = bisect_left(competitors, competitor)
        if i == len(competitors) or competitors[i] != competitor:
            competitors.insert(i, competitor)
        competitor = competitors[i]

        for x in line[10:15]:
            x = int(x)
            if not can_be_discarded(x, wr_index):
                index = normalize_result(x)-wr_index
                competitor.count[index] += 1

    log.info("%s elegible competitors" % len(competitors))

    for i in range(RANGE):
        sorted_i = sorted(
            filter(lambda c: sum_to_index(c, i) > 0, competitors), key=lambda c: sum_to_index(c, i))[::-1]
        print("Sub %s" % (i+wr_index+1))
        for x in sorted_i[:10]:
            print(x.wca_id, sum_to_index(x, i))
        print()


if __name__ == "__main__":
    main()
