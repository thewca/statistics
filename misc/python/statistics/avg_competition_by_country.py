# avg number of comps per country since 2010
# we are taking the average of the number of comps/year since 2010.

import csv
from datetime import datetime

from ..util.html_util import get_competitor_link, html_link_format
from ..util.log_util import log
from ..util.statistics_api_util import create_statistics


def avg_competitions():
    country_list = []
    comp_year = []
    avg_list = []

    max_year = datetime.now().year-1
    year_range = 5
    min_year = max_year - year_range

    header = True

    with open('WCA_export/WCA_export_Competitions.tsv') as tsvin:
        tsvin = csv.reader(tsvin, delimiter='\t')

        for line in tsvin:

            if header:
                header = False
                continue

            year = int(line[5])
            if year < min_year or year > max_year:
                continue

            max_year = max(max_year, year)

            country = line[3]
            if country not in country_list:
                country_list.append(country)
                comp_year.append([])

            i = country_list.index(country)

            while len(comp_year[i]) < year-min_year+1:
                comp_year[i].append(0)

            # 2010 is 0, 2011 is 1, etc. we add 1 for each comp
            comp_year[i][year-min_year] += 1

        for x in comp_year:
            avg_list.append(sum(x)/(year_range+1))

        table = []
        for (x, y, z) in sorted(zip(avg_list, country_list, comp_year))[::-1]:
            table.append([("%.2f" % x).zfill(5), y])
            for competition_number in z:
                table[-1].append(competition_number)

        log.info("Pad empty results with zeroes")
        max_size = max(map(len, table))
        for x in table:
            x += [0] * (max_size - len(x))

        out = {}
        out["title"] = "Average number of competitions per year by country since %s" % min_year
        headers = ["Avg", "Country"]

        for year in range(min_year, max_year+1):
            headers.append(year)

        out["statistics"] = [
            {"keys": [], "content": table, "headers": headers}]
        return out


def main():

    stat = avg_competitions()
    create_statistics(stat)


main()
