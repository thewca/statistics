import os

import mysql.connector

user = os.environ.get('DB_USERNAME', 'root')
host = os.environ.get('DB_HOST', 'localhost')
password = os.environ.get('DB_PASSWORD', '')
database = os.environ.get('DB_DATABASE', 'wca_development')


def get_database_connection():
    return mysql.connector.connect(user=user, password=password,
                                   host=host,
                                   database=database)
