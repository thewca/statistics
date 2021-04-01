#!/bin/bash

# server must be running

echo "Generate all statistics from sql"
curl -X POST "http://localhost:8080/statistics/generate-all-from-sql" -H "accept: */*" -d ""
echo "Sql statistics generated"

echo "Generate python statistics"
for filename in misc/python/statistics/*.py; do
    echo $filename
    to_execute="python3 -m ${filename%.*}"
    module=${to_execute//[\/]/.}
    ($module)
done
echo "Python statistics generated"
