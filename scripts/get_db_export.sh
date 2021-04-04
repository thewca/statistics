#!/bin/bash

# This requires unzip to be installed
# give this file permission to execute and execute it
# 
# ./get_db_export.sh

export_file_zip="wca-developer-database-dump.zip"
export_file_sql="wca-developer-database-dump.sql"

download=true

# First we check if the file exists.
if [ -f $export_file_zip ]; then
    one_week=$(date -d 'now - 7 days' +%s) # export older than 1 week is suggested to be replaced
    date_of_export=$(date -r "$export_file_zip" +%s)

    if (( date_of_export <= one_week )); then # Here we check how old the export is.
        echo "$export_file_zip is older than 7 days."
        echo "Cleaning old data."
        
        rm $export_file_zip;
        download=true;
        
    else
        download=false
        echo "$export_file_zip is up to date."
    fi
fi

if [ "$download" = true ]; then
    echo "Downloading the latest export."
    wget -q https://www.worldcubeassociation.org/wst/wca-developer-database-dump.zip
fi

if [ -f $export_file_sql ]; then
    echo "There's already a sql in this folder. Deleting."
    rm $export_file_sql;
fi

echo "Extracting $export_file_zip"
unzip "$export_file_zip"

echo "Executing the .sql"
echo "This can take a few hours"
sudo mysql -e "create database if not exists wca_development; USE wca_development; SOURCE $export_file_sql;"

echo "Complete"
