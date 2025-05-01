from typing import List

from misc.python.model.event import Event
from misc.python.util.database_util import get_database_connection
from misc.python.util.log_util import log

query = """
select
    id,
    name
from
    events e
where
    -- Exclude inactive
    `rank` < 900
order by
    `rank`
"""


def get_current_events() -> List[Event]:
    log.info("Get current events")

    events = []

    cnx = get_database_connection()
    cursor = cnx.cursor()

    cursor.execute(query)

    for id, name in cursor:
        event = Event(id, name)
        events.append(event)

    cnx.close()

    log.info("Found %s events" % len(events))

    return events
