import os

import mysql.connector

user = os.environ.get('DB_USER', 'root')
host = os.environ.get('DB_HOST', 'locahost')
password = os.environ.get('DB_PASSWORD', '')
database = os.environ.get('DATABASE', 'wca_development')


def get_database_connection():
    return mysql.connector.connect(user=user, password=password,
                                   host=host,
                                   database=database)
