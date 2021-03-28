# WCA Statistics

WCA Statistics is a collection data analysis over the WCA's database.

[![Build Status](https://travis-ci.com/thewca/statistics.svg?branch=main)](https://travis-ci.com/thewca/statistics)

## Requirements

This project is composed of front end and backend. You can check the requirements in each one.

## How to run it

This project is separated in server and client, backend and front and respectively. You can run each one by following instructions in client and server's folder, located in the README.

## Run with docker

- Build server's jar

`cd server && ./gradlew build && cd ..`

- Download the database export and build it in a database called `wca_development`. You can use the file `server/get_db_export.sh`.

- Run docker

`docker-compose up`
