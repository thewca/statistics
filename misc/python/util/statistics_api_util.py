import logging
import requests

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()


url = "http://localhost:8080/statistics/create"


def create_statistics(data):

    log.info("Post data to %s" % url)
    response = requests.post(url, json=data)
    if response.status_code == 200:
        log.info("Success")
    else:
        log.info("Error")
        log.info(response.json())
