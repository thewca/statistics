import logging
import requests
import os

logging.basicConfig(level=logging.INFO)
log = logging.getLogger()

port = os.environ.get("STATISTICS_PORT", "8080")

base_url = f"http://localhost:{port}"


def log_response(response: requests.Response):
    if response.status_code == 200:
        log.info("Success")
    else:
        log.info("Error")
        log.info(response.json())


def create_statistics(data):
    url = f"{base_url}/statistics/create"
    log.info(f"url = {url}")

    response = requests.post(url, json=data)
    log_response(response)


def start_statistics_control(path: str):
    url = f"{base_url}/statistics-control"
    log.info(f"url = {url}")

    response = requests.post(url, params={"path": path})
    log_response(response)


def complete_statistics_control(path: str):
    url = f"{base_url}/statistics-control"
    log.info(f"url = {url}")

    response = requests.patch(url, params={"path": path})
    log_response(response)


def error_statistics_control(path: str, message: str):
    url = f"{base_url}/statistics-control/error"
    log.info(f"url = {url}")

    response = requests.patch(url, params={"path": path, "message": message})
    log_response(response)
