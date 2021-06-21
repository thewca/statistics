#!/bin/bash

# This requires unzip to be installed
# give this file permission to execute and execute it
# 
# ./get_db_export.sh

export_file_zip="wca-developer-database-dump.zip"
export_file_sql="wca-developer-database-dump.sql"
temp_database="wca_development_temp"
new_database="wca_development"

if [ $DB_PASSWORD ]; then
    mysqlconn="sudo mysql -h ${DB_HOST:-localhost} -u ${DB_USER:-root} -P ${DB_PORT:-3306} -p${DB_PASSWORD}"
else
    mysqlconn="sudo mysql"
fi

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

echo "Executing the .sql"
echo "This can take a few hours"
$mysqlconn -e "create database if not exists $temp_database; use $temp_database; source $export_file_sql;"

echo "Rename databases"

$mysqlconn -e "create database if not exists $new_database;"
params=$($mysqlconn -N -e "select table_name from information_schema.tables where table_schema='$temp_database'")

for name in $params; do
      $mysqlconn -e "drop table if exists $new_database.$name; rename table $temp_database.$name to $new_database.$name;";
      echo "Renamed $temp_database.$name to $new_database.$name";
done;

$mysqlconn -e "drop database $temp_database"

echo "Complete"
