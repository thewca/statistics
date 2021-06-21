#!/bin/bash

# server must be running

port=${STATISTICS_PORT:-8006}

echo "Generate all statistics from sql"
curl -X POST "http://localhost:${port}/statistics/generate-from-sql" -H "accept: */*" -d ""
echo "Sql statistics generated"

