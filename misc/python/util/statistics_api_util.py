import logging
import requests
import os

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()

port = os.environ.get("STATISTICS_PORT", "8080")

url = "http://localhost:%s/statistics/create"%port

def create_statistics(data):

    log.info("Post data to %s" % url)
    response = requests.post(url, json=data)
    if response.status_code == 200:
        log.info("Success")
    else:
        log.info("Error")
        log.info(response.json())
