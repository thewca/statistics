#!/bin/bash

# server must be running

port=${STATISTICS_PORT:-8080}

# Download db export
source scripts/get_db_export.sh

echo "Deleting existing statistics"
curl -X DELETE "http://localhost:${port}/statistics" -H "accept: */*"


# Sql stats
source scripts/generate_sql_statistics.sh

# Python
source scripts/generate_python_statistics.sh
