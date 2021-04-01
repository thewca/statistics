def get_competitor_link(wca_id):
    return "https://www.worldcubeassociation.org/persons/%s" % wca_id


def html_link_format(text, link):
    return '<a href="%s">%s</a>' % (link, text)


def get_competition_html_link(competition_id):
    link = "https://www.worldcubeassociation.org/competitions/%s" % competition_id
    return html_link_format(competition_id, link)
