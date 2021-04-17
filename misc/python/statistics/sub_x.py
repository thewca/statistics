# python3 -m misc.python.statistics.sub_x

import csv
from bisect import bisect_left

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.event_util import get_current_events
from misc.python.util.html_util import get_competitor_html_link
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics
from misc.python.util.time_util import time_format

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


def normalize_result(result, event) -> int:
    """WCA stores results in cents. 4 seconds is stored as 400.
    This normalizes the result so it can be used as index."""
    if event == "333fm":
        return result
    return result // 100


def can_be_discarded(result, wr_index, event) -> bool:
    return result < 1 or normalize_result(result, event) >= wr_index + RANGE


def sum_to_index(competitor, i):
    return sum(competitor.count[:i+1])


def sub_x():

    LIMIT = 10

    statistics = {}
    statistics["title"] = "Most Sub-X solves"
    statistics["statistics"] = []
    headers = ["Count", "Name", "Country"]
    statistics["displayMode"] = "GROUPED"

    for current_event in get_current_events():

        competitors = []

        event = current_event.event_id

        if event == "333mbf":
            continue

        log.info("Find sub x for %s" % current_event.name)

        wr_single = find_wr_single(event)
        wr_index = wr_single if event == "333fm" else wr_single // 100
        log.info("WR single for %s is %s" % (event, time_format(wr_single)))

        tsv_file = open("WCA_export/WCA_export_Results.tsv")

        log.info("Compute sub %s results" %
                 (normalize_result(wr_single, event)+RANGE))
        tsvreader = csv.reader(tsv_file, delimiter="\t")
        for line in tsvreader:
            this_event = line[1]
            if this_event != event:  # Also skips header
                continue

            best = int(line[4])

            # We exclude people with DNF or results out of the range
            if can_be_discarded(best, wr_index, event):
                continue

            wca_id = line[7]
            competitor = Competitor(wca_id)

            i = bisect_left(competitors, competitor)
            if i == len(competitors) or competitors[i] != competitor:
                competitor.name = line[6]
                competitor.country = line[8]
                competitors.insert(i, competitor)
            competitor = competitors[i]

            for x in line[10:15]:
                x = int(x)
                if not can_be_discarded(x, wr_index, event):
                    index = normalize_result(x, event)-wr_index
                    competitor.count[index] += 1

        log.info("%s elegible competitors" % len(competitors))

        for i in range(RANGE):
            sorted_i = sorted(
                filter(lambda c: sum_to_index(c, i) > 0, competitors), key=lambda c: sum_to_index(c, i))[::-1]
            c = 0
            prev = None
            stat = []
            for x in sorted_i:
                s = sum_to_index(x, i)

                # Ties
                if c >= LIMIT and s != prev:
                    break

                stat.append([s, get_competitor_html_link(
                    x.wca_id, x.name), x.country])

                prev = s
                c += 1
            index = i+wr_index+1
            current_sub = index if event == "333fm" else index*100
            statistics["statistics"].append(
                {"keys": [current_event.name, "Sub %s" % time_format(current_sub, event)], "content": stat, "headers": headers})

    return statistics


def main():
    log.info(" ========== Sub x ==========")
    statistics = sub_x()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
