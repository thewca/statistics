class Event:
    def __init__(self, event_id, name):
        self.event_id = event_id
        self.name = name

    def __repr__(self) -> str:
        return "Event[event_id=%s, name=%s]" % (self.event_id, self.name)
