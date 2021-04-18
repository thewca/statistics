# python3 -m misc.python.statistics.avg_number_of_events_per_competition_by_country

from datetime import datetime

import pandas as pd

from ..util.log_util import log
from ..util.statistics_api_util import create_statistics


def avg_events():

    current_year = datetime.now().year
    range = 5

    min_year = current_year - range
    max_year = current_year - 1

    country_list = []
    event_list = []

    log.info("Read csv")
    data = pd.read_csv('WCA_export/WCA_export_Competitions.tsv', sep='\t')
    for country, events, year in zip(data["countryId"], data["eventSpecs"], data["year"]):

        if int(year) < min_year or int(year) > max_year:
            continue

        if country not in country_list:
            country_list.append(country)
            event_list.append([])

        i = country_list.index(country)

        events = events.split()
        event_list[i].append(len(events))

    for x in event_list:
        avg_list = map(lambda l: sum(l)/len(l), event_list)

    table = []
    for (x, y) in sorted(zip(avg_list, country_list))[::-1]:
        table.append([("%.2f" % x).zfill(5), y])

    out = {}
    out["title"] = "Avg number of events in a competition for each country from %s to %s" % (
        min_year, max_year)
    headers = ["Avg", "Country"]
    out["statistics"] = [
        {"keys": [], "content": table, "headers": headers, "showPositions": True, "positionTieBreakerIndex": 0}]
    out["table"] = table

    return out


def main():
    stat = avg_events()
    create_statistics(stat)


main()
