# python3 -m misc.python.statistics.sum_of_all_ranks

from bisect import bisect_left

from misc.python.model.event import Event as Ev
from misc.python.statistics.sub_x import Competitor as Comp
from misc.python.util.database_util import get_database_connection
from misc.python.util.log_util import log
from misc.python.util.statistics_api_util import create_statistics
from misc.python.util.html_util import get_competitor_html_link


LIMIT = 10

query_events = """
select
    e.id,
    e.name,
    max_ranks.max_rank
from
    Events e
    inner join (
        select
            eventId,
            max(worldRank) + 1 max_rank
        from
            Ranks%s
        group by
            eventId
    ) max_ranks on max_ranks.eventId = e.id -- Exclude inactive events
where
    `rank` < 900
order by
    `rank`
"""

# Repalce %s with Average or Single for different table
query_competitors = """
select
    personId,
    worldRank,
    eventId,
    p.name
from
    Ranks%s r
    inner join Persons p on r.personId = p.id
where
    eventId in (
        select
            id
        from
            Events
        where
            `rank` < 900
    )
"""


class Competitor(Comp):
    def __init__(self, wca_id, events):
        super().__init__(wca_id)

        # Every competitor is considered as the mas rank at first
        self.ranks = list(map(lambda e: e.max_rank, events))
        self.sum_of_ranks = None

    def __repr__(self) -> str:
        return "Competitor[id=%s, name=%s, sum_of_ranks=%s]" % (self.wca_id, self.name, self.sum_of_ranks)


class Event(Ev):
    def __init__(self, event_id, name, max_rank):
        super().__init__(event_id, name)
        self.max_rank = max_rank


def sum_of_all_ranks():

    cnx = get_database_connection()
    cursor = cnx.cursor()

    statistics = {}
    statistics["title"] = "Sum of all ranks"
    statistics["statistics"] = []

    for type in ["Average", "Single"]:

        events = []
        cursor.execute(query_events % type)  # Not an sql replacement

        for event_id, event_name, max_rank in cursor:
            event = Event(event_id, event_name, max_rank)
            events.append(event)
        log.info("Found %s events" % len(events))

        event_ids = list(map(lambda e: e.event_id, events))

        cursor.execute(query_competitors % type)  # Not an sql replacement

        competitors = []

        prev_event = None
        event_index = None

        log.info("Handle competitors ranks")
        for (wca_id, world_rank, event_id, name) in cursor:
            competitor = Competitor(wca_id, events)

            index = bisect_left(competitors, competitor)
            if index == len(competitors) or competitor != competitors[index]:
                competitor.name = name
                competitors.insert(index, competitor)
            competitor = competitors[index]

            if event_id != prev_event:
                event_index = event_ids.index(event_id)
                prev_event = event_id

            competitor.ranks[event_index] = world_rank
        log.info("Found %s competitors" % len(competitors))

        log.info("Fill sum of ranks")
        for competitor in competitors:
            competitor.sum_of_ranks = sum(competitor.ranks)

        log.info("Sort by sum of ranks")
        competitors = sorted(competitors, key=lambda c: c.sum_of_ranks)

        c = 0
        prev = None
        stat = []
        for competitor in competitors:
            s = competitor.sum_of_ranks
            if c >= LIMIT and s != prev:
                break
            prev = s

            # Mark some results as red
            for i in range(len(events)):
                if competitor.ranks[i] == events[i].max_rank:
                    competitor.ranks[i] = "<span style=\"color:red\">%s</span>" % (
                        competitor.ranks[i])

            stat.append([get_competitor_html_link(competitor.wca_id,
                                                  competitor.name), s, *competitor.ranks])
            c += 1

        headers = ["Name", "Sum", *map(lambda c:c.name, events)]

        statistics["statistics"].append(
            {"keys": [type], "content": stat, "headers": headers, "showPositions": True, "positionTieBreakerIndex": 1})

        print(event_ids)

    cnx.close()

    return statistics


def main():
    log.info(" ========== Sum of all ranks ==========")
    statistics = sum_of_all_ranks()
    create_statistics(statistics)


if __name__ == "__main__":
    main()
