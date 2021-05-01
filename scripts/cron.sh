#!/bin/bash

echo "Build artifact"
./server/gradlew build -p server -x test

echo "Restart process"
sudo kill -9 `sudo lsof -t -i:8080`
java -jar server/build/libs/server-*.jar &

echo "Get database export and generate statistics"
./scripts/generate_all_statistics.sh

