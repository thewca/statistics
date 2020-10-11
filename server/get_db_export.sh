#!/bin/bash

export_file_zip="wca-developer-database-dump.zip"
export_file_sql="wca-developer-database-dump.sql"

# First we check if the file exists.
if [ -f $export_file_zip ]; then
    echo "There's already a zip in this folder. Deleting."
    rm $export_file_zip;
fi

if [ -f $export_file_sql ]; then
    echo "There's already a sql in this folder. Deleting."
    rm $export_file_sql;
fi

echo "Downloading the latest export."
wget -q https://www.worldcubeassociation.org/wst/wca-developer-database-dump.zip

echo "Extracting $export_file_zip"
unzip "$export_file_zip"

echo "Executing the .sql"
echo "This can take a few hours"
sudo mysql -e "create database if not exists wca_development; USE wca_development; SOURCE $export_file_sql;"
