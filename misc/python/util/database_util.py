import os
import re

import mysql.connector
from misc.python.util.log_util import log

user = os.environ.get('DB_USERNAME', 'root')
host = os.environ.get('DB_HOST', 'localhost')
password = os.environ.get('DB_PASSWORD', '')
database = os.environ.get('DB_DATABASE', 'wca_development')


def get_database_connection():
    return mysql.connector.connect(user=user, password=password,
                                   host=host,
                                   database=database)


def __get_file_name(sql_file_name):
    return f'misc/python/sql/{sql_file_name}.sql'


def sql_replace(query_str):
    '''Replace sql-onic :PLACEHOLDER with pythonic %(PLACEHOLDER)s'''
    for y in re.findall(":[a-zA-Z]{1}[a-zA-Z0-9_]+", query_str):
        query_str = query_str.replace(y, f'%({y[1:]})s')
    return query_str


def execute_query(sql_file_name, values=()):
    try:
        with open(__get_file_name(sql_file_name), encoding='utf-8') as query:
            query_str = sql_replace(query.read())
            cursor.execute(query_str, values)

            return cursor.fetchall()

    except Exception as e:
        raise Exception(e)


cnx = get_database_connection()
cursor = cnx.cursor(dictionary=True)
