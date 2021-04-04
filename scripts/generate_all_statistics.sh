#!/bin/bash

# server must be running

echo "Deleting existing statistics"
curl -X DELETE "http://localhost:8080/statistics" -H "accept: */*"

# Download db export
./scripts/get_db_export.sh

# Download tsv export and build ordered results
./scripts/get_tsv_export.sh

# Sql stats
./scripts/generate_sql_statistics.sh

# Python
./scripts/generate_python_statistics.sh
