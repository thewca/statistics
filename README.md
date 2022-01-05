# WCA Statistics

WCA Statistics is a collection data analysis over the WCA's database.

[![Backend](https://github.com/thewca/statistics/actions/workflows/backtest.yml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/backtest.yml)
[![Frontend](https://github.com/thewca/statistics/actions/workflows/fronttest.yml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/fronttest.yml)
[![Deploy frontend](https://github.com/thewca/statistics/actions/workflows/frontdeploy.yml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/frontdeploy.yml)

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

## Docker cron

The file `scripts/cron-docker.sh` is used to make a fresh new download of the ropository and run the statistics over it. In the process or calculating statistics, the other `sh` files inside of `scripts` are used.

### Deploy the cron image

The cron script also runs in its own container. This helps when running this periodically like in a cron schedule, using AWS Batch

- Build the image

  `docker build -t thewca/statistics-cron .`

- Deploy the image

  `docker push thewca/statistics-cron`

### Run the image locally

You can also run this image locally. This image will download the export, update the database and calculate all the statistics.

  `docker run thewca/statistics-cron`

## Run cron

- Export variables

```
export STATISTICS_PORT=8080
export DB_HOST={{SENSITIVE}}
export DB_DATABASE={{SENSITIVE}}
export DB_USERNAME={{SENSITIVE}}
export DB_PASSWORD={{SENSITIVE}}
export DB_PORT=3306
```

- Run it

  `source scripts/cron.sh`

