# WCA Statistics Client

[![Frontend](https://github.com/thewca/statistics/actions/workflows/fronttest.yaml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/fronttest.yaml)
[![Deploy frontend](https://github.com/thewca/statistics/actions/workflows/frontdeploy.yaml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/frontdeploy.yaml)

## Requirements

- [Node](https://nodejs.org/)

- [Yarn](https://classic.yarnpkg.com/en/docs/install)

## How to run it

The commands listed here should work in Unix systems or in Windows (using GitBash commandline) with maven set.

- Clone this repository

`git clone https://github.com/thewca/statistics.git`

- Navigate to the clients's folder

`cd statistics/client`

- Install dependencies

`yarn install`

- Run the client

`yarn start`

- Run local

`yarn start:local`

## Run with docker

- Build an image

`docker build -t user/statistics-client .`

- Run the image

`docker run -d -p 3000:3000 --name statistics-client user/statistics-client:latest`

The `-d` part means "detached", so you'll have to stop by killing the process running on port 3000.

## Deploy

Deploy is made using GH Actions.

## Checkstyle

We use prettier for checking style. After code, you can run `yarn prettier_write` for auto formatting or `yarn prettier` for simple check.
