# python3 -m misc.python.statistics.record_evolution


import datetime

from misc.python.model.competitor import Competitor as Comp
from misc.python.util.database_util import (execute_query,
                                            get_database_connection)
from misc.python.util.event_util import get_current_events
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics

title = "Record Evolution"


def record_evolution():

    log.info("Get database connection")

    statistics = {}
    statistics["title"] = title
    statistics["groupName"] = "Results"
    statistics["statistics"] = []
    headers = ["Count", "Name", "Country"]
    statistics["displayMode"] = "GROUPED"

    for current_event in get_current_events():
        if current_event.event_id != '333':
            continue

        log.info(f'Current event {current_event.event_id}')

        dates = execute_query(
            'record_evolution/get_dates', {'EVENT_ID': current_event.event_id})
        log.info(f'Found {len(dates)} dates')

        for current_date in dates:
            results = execute_query(
                'record_evolution/next_results', {'CURRENT_DATE': current_date["start_date"]})

    return statistics


def main():
    log.info("========== %s ==========" % title)
    evolution = record_evolution()
    # create_statistics(evolution)


if __name__ == "__main__":
    main()
