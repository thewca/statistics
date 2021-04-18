class Competitor:
    wca_id = None
    best = None
    country = None
    name = None

    def __init__(self, wca_id):
        self.wca_id = wca_id

    def __eq__(self, o):
        return self.wca_id == o.wca_id

    def __lt__(self, o):
        return self.wca_id < o.wca_id
