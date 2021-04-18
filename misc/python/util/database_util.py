import mysql.connector


def get_database_connection():
    return mysql.connector.connect(user='root', password='',
                                   host='localhost',
                                   database='wca_development')
