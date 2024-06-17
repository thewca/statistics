# WCA Statistics Client

[![Frontend](https://github.com/thewca/statistics/actions/workflows/fronttest.yaml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/fronttest.yaml)
[![Deploy frontend](https://github.com/thewca/statistics/actions/workflows/frontdeploy.yaml/badge.svg)](https://github.com/thewca/statistics/actions/workflows/frontdeploy.yaml)

## Requirements

- [NVM](https://github.com/nvm-sh/nvm) or [the correct node version](.nvmrc).

## How to run it

The commands listed here should work in Unix systems or in Windows (using GitBash commandline) with maven set.

- Clone this repository

`git clone https://github.com/thewca/statistics.git`

- Navigate to the clients folder

`cd statistics/client`

- Select the correct node version (you can skip this step if your `node -v` results the same as [the expected one](.nvmrc))

`nvm use`

If that fails, maybe you will need to run `nvm install`

- Install dependencies

`npm install`

- Run the client

`npm start`

## Checkstyle

We use prettier for checking style. After code, you can run `npx prettier --check .` for simple check.
