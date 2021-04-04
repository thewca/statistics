#!/bin/bash

# server must be running

echo "Generate all statistics from sql"
curl -X POST "http://localhost:8080/statistics/generate-all-from-sql" -H "accept: */*" -d ""
echo "Sql statistics generated"

