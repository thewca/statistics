# WCA Statistics Server

## Requirements

- [Java](https://www.java.com/pt-BR/)

- [MySQL](https://dev.mysql.com/doc/refman/8.0/en/linux-installation.html)

- [Lombok](https://projectlombok.org/)

## Setup local database

You should an internal database for handling WCA data.

In case you do not have it installed yet, you will need to get MySQL.

- Install [MySQL 8.0](https://dev.mysql.com/doc/refman/8.0/en/linux-installation.html), and set it up with a user with username "root" with an empty password.

```
sudo mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '';

create database wca_development;
```

The database `wca_development` will be populated with WCA data. If you want to change password, username or others, make sure to also change on `application-local.properties`.

## Before you run this

You need your copy of the database from WCA. If you already have it (with a user 'root' with no password), you can skip this.

Download [the latest export](https://www.worldcubeassociation.org/wst/wca-developer-database-dump.zip) and execute the sql (as stated in the last step). If you wish, you can execute the file `get_db_export.sh` in the scripts folder.

From the root folder, use

```
chmod +x scripts/get_db_export.sh
./scripts/get_db_export.sh
```

It will ask you to run in sudo mode in order to execute the sql.

## How to run it

The commands listed here should work in Unix systems or in Windows (using GitBash commandline) with maven set.

- Clone this repository

`git clone https://github.com/thewca/statistics.git`

- Navigate to the server's folder

`cd statistics/server`

- Run the server

`./gradlew bootRun --args='--spring.profiles.active=local'`

An address should be logged. Probably http://localhost:8000/swagger-ui.html#/, if you did not change port. Visit it to read the documentation. You can run in another port (let's say 8001) by using `./gradlew bootRun --args='--spring.profiles.active=local --server.port=8001'`.

## Run with docker

- Build an image

`docker build -t {user}/statistics-server .`

- Run the image

`docker run -d -p 8080:8080 --network=host --name statistics-server {user}/statistics-server:latest`

The `-d` part means "detached", so you'll have to stop by killing the process running on port 8080.
