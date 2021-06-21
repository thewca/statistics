#!/bin/bash

# This process runs in a different port so it has endpoints that are not protected and can,
# therefore, be used to create statistics

port=${STATISTICS_PORT:-8080}

LIMIT=60 # 60 seconds waiting for a response
INTERVAL=5

echo "Build artifact"
./server/gradlew build -p server -x test

echo "Start the API in the port $port"
java -jar server/build/libs/server-*.jar --server.port=${port}&

elapsed=0
while [ 1 ]; do
    sleep "$INTERVAL"

    response=$(curl -s "http://localhost:${port}/actuator/health")

    # Check if response length is greater than 0
    if [ "${#response}" -gt 0 ]
    then
        echo "API Started"

        break
    fi

    ((elapsed+=INTERVAL))

    echo "Waiting for the API to start ${elapsed}/${LIMIT}"

    if [ "$elapsed" -gt "$LIMIT" ]
    then
        echo "API not started. Finishing."

        exit 1
    fi

done

echo "Get database export and generate statistics"
./scripts/generate_all_statistics.sh

echo "Kill the process on port ${port}"
sudo kill -9 `sudo lsof -t -i:${port}`
