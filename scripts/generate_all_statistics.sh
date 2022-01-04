#!/bin/bash

# server must be running

port=${STATISTICS_PORT:-8080}

# Download db export
source scripts/get_db_export.sh

echo "Deleting existing statistics"
curl -X DELETE "http://localhost:${port}/statistics" -H "accept: */*"

echo "Generate SQL stats"
source scripts/generate_sql_statistics.sh

echo "Generate python stats"
source scripts/generate_python_statistics.sh

echo "Generate best ever ranks"
curl -X POST "http://localhost:${port}/best-ever-rank/generate/all" -H "accept: */*" -d ""
