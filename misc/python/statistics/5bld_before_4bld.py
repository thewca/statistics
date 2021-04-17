# python3 -m misc.python.statistics.5bld_before_4bld

import bisect
import csv
import datetime

from ..util.html_util import (get_competition_html_link, get_competitor_link,
                              html_link_format)
from ..util.statistics_api_util import create_statistics
from ..util.time_util import time_format


def compare_results(ev1, ev2):
    """Checks if a competitor got ev1 before ev2, expect for 333mbf and 333mbo, due to time encode."""

    wca_ids = []
    names = []
    first_results = []
    first_competition = []
    first_date = []
    diffs = []

    with open('WCA_export/WCA_export_Results_Ordered.tsv') as tsvin:
        tsvin = csv.reader(tsvin, delimiter='\t')

        evs = [ev1, ev2]
        for line in tsvin:
            event = line[1]
            if event in evs:
                wca_id = line[7]

                i = bisect.bisect_left(wca_ids, wca_id)

                if i == len(wca_ids) or wca_ids[i] != wca_id:
                    name = line[6]

                    wca_ids.insert(i, wca_id)
                    names.insert(i, name)

                    first_results.insert(i, [None, None])
                    first_competition.insert(i, [None, None])
                    first_date.insert(i, [None, None])
                    diffs.insert(i, None)

                competition = line[0]

                best = int(line[4])
                j = evs.index(event)
                if best > 0 and first_results[i][j] == None:
                    first_results[i][j] = time_format(best)
                    first_competition[i][j] = competition

                    year = int(line[17])
                    month = int(line[18])
                    day = int(line[19])
                    date = datetime.date(year, month, day)
                    first_date[i][j] = date

                    if first_date[i][0] != None and first_date[i][1] != None:
                        diff = (first_date[i][1]-first_date[i][0]).days
                        diffs[i] = diff

    table = []

    out_diffs = []
    out_ids = []
    out_names = []
    out_ev1_results = []
    out_ev2_results = []
    out_competition_ev1 = []
    out_competition_ev2 = []
    for i in range(len(wca_ids)):
        if diffs[i] != None and diffs[i] > 0:
            out_diffs.append(diffs[i])
            out_ids.append(wca_ids[i])
            out_names.append(names[i])

            out_ev1_results.append(first_results[i][0])
            out_ev2_results.append(first_results[i][1])

            out_competition_ev1.append(first_competition[i][0])
            out_competition_ev2.append(first_competition[i][1])

    count = 1
    for diff, wca_id, name, result1, comp1, result2, comp2 in sorted(zip(out_diffs, out_ids, out_names, out_ev1_results, out_competition_ev1, out_ev2_results, out_competition_ev2))[::-1]:

        link = get_competitor_link(wca_id)
        table.append([diff, html_link_format(name, link), result1, get_competition_html_link(
            comp1), result2, get_competition_html_link(comp2)])

        count += 1

    headers = ["Days", "Name", "First result %s" %
               ev1, "Competition", "First result %s" % ev2, "Competition"]
    out = {}
    out["title"] = "Competitors who got 5BLD before 4BLD"
    out["explanation"] = "In case of multiple first results (eg. ao3), best one is taken."
    out["statistics"] = [{"keys": [], "content": table,
                          "headers": headers, "showPositions": True, "positionTieBreakerIndex": 0}]

    return out


def main():

    ev1 = "555bf"
    ev2 = "444bf"

    data = compare_results(ev1, ev2)

    create_statistics(data)


main()
