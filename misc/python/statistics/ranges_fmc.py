import bisect
import csv
import logging

import requests

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()


def largest_range(lista):

    i = 0
    r = 1
    max_r = 0
    min_range = -1  # where the range started
    max_range = -1  # where the range ended
    STEP = 1  # if you want ranges in 2 (eg. 4, 6, 8), change here

    range_start = lista[i]

    while i < len(lista)-1:

        if lista[i+1]-lista[i] == STEP:
            r += 1
        else:
            if r >= max_r:
                max_r = r
                max_range = lista[i]
                min_range = range_start

            range_start = lista[i+1]
            r = 1

        i += 1

    if r > max_r:
        max_r = r
        max_range = lista[i]
        min_range = range_start

    # len of range, start, end
    return (max_r, min_range, max_range)


def get_competitor_link(wca_id):
    return "https://www.worldcubeassociation.org/persons/%s" % wca_id


def html_link_format(text, link):
    return '<a href="%s">%s</a>' % (link, text)


def get_competition_html_link(competition_id):
    link = "https://www.worldcubeassociation.org/competitions/%s" % competition_id
    return html_link_format(competition_id, link)


def largest_range_fmc():

    LIMIT = 50
    lists_of_results = []
    id_list = []
    name_list = []
    country_list = []

    log.info("Read tsv")
    with open('WCA_export/WCA_export_Results.tsv') as tsvin:
        tsvin = csv.reader(tsvin, delimiter='\t')

        skip_header = True
        for line in tsvin:

            event = line[1]
            if skip_header or event != "333fm":
                skip_header = False
                continue

            name = line[6]
            wca_id = line[7]
            country = line[8]
            i = bisect.bisect_left(id_list, wca_id)
            if i == len(id_list) or id_list[i] != wca_id:
                name_list.insert(i, name)
                id_list.insert(i, wca_id)
                lists_of_results.insert(i, [])
                country_list.insert(i, country)

            for x in line[10:13]:
                x = int(x)
                if x > 0:
                    j = bisect.bisect_left(lists_of_results[i], x)
                    if j == len(lists_of_results[i]) or lists_of_results[i][j] != x:
                        lists_of_results[i].insert(j, x)

    name_out = []
    range_out = []
    min_out = []
    max_out = []
    id_list_out = []
    country_list_out = []

    log.info("Calculate ranges")
    for i in range(len(id_list)):
        if len(lists_of_results[i]) < 1:  # skipping people with only 1 result
            continue
        name_out.append(name_list[i])
        id_list_out.append(id_list[i])
        country_list_out.append(country_list[i])
        a, b, c = largest_range(lists_of_results[i])
        range_out.append(a)
        min_out.append(b)
        max_out.append(c)

    log.info("Build results")
    table = []
    prev = None
    count = 0
    for a, b, c, name, country, wca_id in sorted(zip(range_out, min_out, max_out, name_out, country_list_out, id_list_out))[::-1]:
        count += 1

        if count >= LIMIT and prev != a:
            break
        link = get_competitor_link(wca_id)
        table.append([a, html_link_format(name, link), country, b, c])

        prev = a
    out = {}
    out["title"] = "Range in FMC"
    out["explanation"] = "Range here means no gap"
    headers = ["Range", "Person",
               "Country", "Range Start", "Range End"]
    out["statistics"] = [{"keys": [], "content": table, "headers": headers}]
    return out


def main():
    data = largest_range_fmc()

    url = "http://localhost:8080/statistics/create"

    log.info("Post data to %s" % url)
    response = requests.post(url, json=data)
    if response.status_code == 200:
        log.info("Success")
    else:
        log.info("Error")


if __name__ == "__main__":
    main()
