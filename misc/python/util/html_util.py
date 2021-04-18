def get_competitor_link(wca_id):
    return "https://www.worldcubeassociation.org/persons/%s" % wca_id


def html_link_format(text, link):
    return '<a href="%s">%s</a>' % (link, text)


def get_competition_html_link(competition_id, competition_name=None):
    link = "https://www.worldcubeassociation.org/competitions/%s" % competition_id
    return html_link_format(competition_name if competition_name else competition_id, link)


def get_competitor_html_link(wca_id, name=None):
    """Given a wca_id and a name (optional, defaults to wca_id), returns the html link that can be displayed.
    Provided link href will redirect to the competitors profile and name will appear as the text."""
    return html_link_format(name if name else wca_id, get_competitor_link(wca_id))
