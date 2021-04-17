# From the root
# python3 -m misc.python.statistics.ranges

import bisect
import csv
import logging
from misc.python.util.mbld_util import get_mbld_points
from misc.python.util.event_util import get_current_events

from ..util.html_util import get_competitor_link, html_link_format
from ..util.range_util import largest_range
from ..util.statistics_api_util import create_statistics
from ..util.time_util import time_format

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()


def has_multiple_results(results):
    if len(results) < 2:
        return False
    for i in range(1, len(results)):
        if results[i] != results[i-1]:
            return True
    return False


def ranges():
    LIMIT = 10

    out = {}
    out["title"] = "Ranges"
    out["displayMode"] = "SELECTOR"
    headers = ["Range", "Person",
               "Country", "Range Start", "Range End"]
    out["statistics"] = []

    current_events = get_current_events()

    for current_event in current_events:
        lists_of_results = []
        id_list = []
        name_list = []
        country_list = []

        event = current_event.event_id
        log.info("Event = %s" % current_event.name)

        log.info("Read tsv")
        with open('WCA_export/WCA_export_Results.tsv') as tsvin:
            tsvin = csv.reader(tsvin, delimiter='\t')

            for line in tsvin:

                # Also skips header
                # line[4] is the best result
                if line[1] != event or int(line[4]) < 1:
                    continue

                name = line[6]
                wca_id = line[7]
                country = line[8]
                i = bisect.bisect_left(id_list, wca_id)
                if i == len(id_list) or id_list[i] != wca_id:
                    name_list.insert(i, name)
                    id_list.insert(i, wca_id)
                    lists_of_results.insert(i, [])
                    country_list.insert(i, country)

                for x in line[10:15]:
                    if x in ["-2", "-1", "0"]:
                        continue
                    if event == "333mbf":
                        points = get_mbld_points(x)[0]

                        j = bisect.bisect_left(lists_of_results[i], points)
                        if j == len(lists_of_results[i]) or lists_of_results[i][j] != points:
                            lists_of_results[i].insert(j, points)
                    else:
                        x = int(x)
                        j = bisect.bisect_left(lists_of_results[i], x)
                        if j == len(lists_of_results[i]) or lists_of_results[i][j] != x:
                            lists_of_results[i].insert(j, x)

        name_out = []
        range_out = []
        min_out = []
        max_out = []
        id_list_out = []
        country_list_out = []

        log.info("Organize ranges")
        for i in range(len(id_list)):
            # skipping people with only 1 result
            range_size, range_s, range_e = largest_range(lists_of_results[i])

            if range_size == 1:
                continue

            name_out.append(name_list[i])
            id_list_out.append(id_list[i])
            country_list_out.append(country_list[i])
            range_out.append(range_size)
            min_out.append(range_s)
            max_out.append(range_e)

        table = []

        log.info("Compute table")
        prev = None
        count = 0
        for range_size, range_s, range_e, name, country, wca_id in sorted(zip(range_out, min_out, max_out, name_out, country_list_out, id_list_out))[::-1]:
            count += 1

            if count > LIMIT and prev != range_size:
                break

            if current_event not in ("333fm", "333mbf"):
                range_s = time_format(range_s)
                range_e = time_format(range_e)
            link = get_competitor_link(wca_id)
            table.append([range_size, html_link_format(
                name, link), country, range_s, range_e])

            prev = range_size

        explanation = "Competitors that got all results from the range start to the range end in %s" % ("steps of 1" if event in (
            "333fm", "333mbf") else "step of 0.01")
        out["statistics"].append(
            {"keys": [current_event.name], "content": table, "headers": headers, "explanation": explanation})

    return out


def main():
    log.info(" ========== Ranges ==========")
    out = ranges()

    create_statistics(out)


main()
