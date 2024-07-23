#!/bin/bash

# This requires unzip to be installed
# give this file permission to execute and execute it
# 
# ./get_db_export.sh

export_file_zip="wca-developer-database-dump.zip"
export_file_sql="wca-developer-database-dump.sql"
database_name="wca_development"

download=true

# First we check if the file exists.
if [ -f $export_file_sql ]; then
    one_week=$(date -d 'now - 7 days' +%s) # export older than 1 week is suggested to be replaced
    date_of_export=$(date -r "$export_file_sql" +%s)

    if (( date_of_export <= one_week )); then # Here we check how old the export is.
        echo "$export_file_sql is older than 7 days."
        echo "Cleaning old data."
        
        rm $export_file_sql;
        download=true;
        
    else
        download=false
        echo "$export_file_sql is up to date."
    fi
fi

if [ "$download" = true ]; then
    echo "Downloading the latest export."
    wget -q https://www.worldcubeassociation.org/wst/wca-developer-database-dump.zip

    if [ -f $export_file_sql ]; then
        echo "There's already a sql in this folder. Deleting."
        rm $export_file_sql;
    fi

    echo "Extracting $export_file_zip"
    unzip "$export_file_zip"

    echo "Remove the zip file"
    rm $export_file_zip
fi

# https://jira.mariadb.org/browse/MDEV-34183
# mariadb generates a strange first line. we ignore it
python3 scripts/remove_first_line.py

echo "Executing the .sql"
echo "This can take a few hours"


mysql -h ${DB_HOST:-localhost} -u ${DB_USERNAME:-root} -P ${DB_PORT:-3306} --password=${DB_PASSWORD:-} -e "start transaction; drop database if exists $database_name; create database $database_name; use $database_name; source $export_file_sql; commit;"

echo "Complete"
