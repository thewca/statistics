import csv
from typing import List

from misc.python.model.event import Event
from misc.python.util.log_util import log


def get_current_events() -> List[Event]:
    log.info("Get current events")

    events = []

    log.info("Read tsv")
    tsv_file = open("WCA_export/WCA_export_Events.tsv")
    tsvreader = csv.reader(tsv_file, delimiter="\t")

    # Skip header
    next(tsvreader, None)

    for line in tsvreader:
        rank = int(line[2])

        # Inavtive events
        if rank > 900:
            continue

        id = line[0]
        name = line[1]
        event = Event(id, name, rank)
        events.append(event)

    # Sort by rank
    return sorted(events, key=lambda e: e.rank)
