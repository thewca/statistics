# From the root python3 -m misc.python.statistics.ranges_mbld

import bisect
import csv
import logging

from ..util.html_util import get_competitor_link, html_link_format
from ..util.range_util import largest_range
from ..util.statistics_api_util import create_statistics

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()


def largest_range_fmc():

    LIMIT = 50
    lists_of_results = []
    id_list = []
    name_list = []
    country_list = []

    log.info("Read tsv")
    with open('WCA_export/WCA_export_Results.tsv') as tsvin:
        tsvin = csv.reader(tsvin, delimiter='\t')

        skip_header = True
        for line in tsvin:

            event = line[1]
            if skip_header or event != "333mbf":
                skip_header = False
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

            for x in line[10:13]:
                if x in ["-2", "-1", "0"]:
                    continue
                missed = int(x[-2:])
                DD = int(x[:2])
                points = 99-DD

                j = bisect.bisect_left(lists_of_results[i], points)
                if j == len(lists_of_results[i]) or lists_of_results[i][j] != points:
                    lists_of_results[i].insert(j, points)

    name_out = []
    range_out = []
    min_out = []
    max_out = []
    id_list_out = []
    country_list_out = []

    for i in range(len(id_list)):
        if len(lists_of_results[i]) < 1:  # skipping people with only 1 result
            continue
        name_out.append(name_list[i])
        id_list_out.append(id_list[i])
        country_list_out.append(country_list[i])
        a, b, c = largest_range(lists_of_results[i])
        range_out.append(a)
        min_out.append(b)
        max_out.append(c)

    table = []

    prev = None
    count = 0
    for a, b, c, name, country, wca_id in sorted(zip(range_out, min_out, max_out, name_out, country_list_out, id_list_out))[::-1]:
        count += 1

        if count >= LIMIT and prev != a:
            break
        link = get_competitor_link(wca_id)
        table.append([a, html_link_format(name, link), country, b, c])

        prev = a
    out = {}
    out["title"] = "Range in MBLD"
    out["explanation"] = "Range here means no gap"
    headers = ["Range", "Person",
               "Country", "Range Start", "Range End"]
    out["statistics"] = [{"keys": [], "content": table, "headers": headers}]
    return out


def main():
    out = largest_range_fmc()

    create_statistics(out)


main()
