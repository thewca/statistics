# WCA Statistics

WCA Statistics is a collection data analysis over the WCA's database.

[![Build Status](https://travis-ci.com/thewca/statistics.svg?branch=main)](https://travis-ci.com/thewca/statistics)

## Requirements

This project is composed of front end and backend. You can check the requirements in each one.

## How to run it

This project is separated in server and client, backend and front and respectively. You can run each one by following instructions in client and server's folder, located in the README.

## Run with docker

- Build server's jar

`./server/gradlew build -p server` (or navigate to the server folder and execute `./gradlew build`)

- Download the database export and build it in a database called `wca_development`. You can use the file `server/get_db_export.sh` with

```
chmod +x server/get_db_export.sh
./server/get_db_export.sh
```

- Run docker

`docker-compose up`

## Generate all statistics

First, you'll need to start the server, then you can execute

```
chmod +x scripts/generate_all_statistics.sh
./scripts/generate_all_statistics.sh
```

## Deploy

Frontend

```
cd client
PUBLIC_URL="{OMMITED FOR NOW}" REACT_APP_BASE_URL="{OMMITED FOR NOW}" yarn build

```
