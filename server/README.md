# WCA Statistics Server

## Requirements

- [Java](https://www.java.com/pt-BR/)

- [MySQL](https://dev.mysql.com/doc/refman/8.0/en/linux-installation.html)

## Setup local database

### Using docker

If you are in the root folder, you can just run `docker-compose up -d`. This will spin up an empty MySQL database running on port 3306, it will also download the latest WCA dump and apply it for you.

### Your own local copy of WCA's database

If you are familiar with the development for the WCA ecosystem, it is likely that you have your own database running. You can reuse that. Just make sure that the connection string in `server/src/main/resources/application-local.yaml` fits your case.

## How to run it

- Clone this repository

`git clone https://github.com/thewca/statistics.git`

### With your IDE

- Open the `server` folder in your favorite IDE (not the root folder)
- The project uses gradle and your IDE should recognize that.
- You can hit the run (or debug) button to run it.

### With CLI

Navigate to the server's folder (Unix systems)

`cd statistics/server`

- Run the server

`./gradlew bootRun`

An address should be logged. Probably http://localhost:8080/swagger-ui.html#/, if you did not change port. Visit it to read the documentation. You can run in another port (let's say 8001) by using `./gradlew bootRun --args='--spring.profiles.active=local --server.port=8001'`.

For Windows, I think you need to use `.\gradlew.bat bootRun`.

## Run with docker

- Build an image

`docker build -t {user}/statistics-server .`

- Run the image

`docker run -d -p 8080:8080 --name statistics-server {user}/statistics-server:latest`

The `-d` part means "detached", so you'll have to stop by killing the process running on port 8080.

## Tests

This backend project uses integration tests so we need to actually connect to a database and fetch data from somewhere.

- Preparing the database for tests (from the repository root)

  `docker-compose -f server/docker-compose-test.yaml up -d`

This will start the database (port 3307) and also a mocked version of the WCA's api (for getting user info) on port 3500.

- Run the tests

In a new terminal, from the repository root, run

    ./server/gradlew clean build -p server --info

- If you need to change migrations, run

  ```
  docker-compose -f server/docker-compose-test.yaml down --volumes
  docker-compose -f server/docker-compose-test.yaml up -d
  ```

## Code format

### Intelij

- `File/Settings`
- `Editor/Code style`
- `Scheme actions/Import schemes/IntelliJ IDEA...`
- Import file `server/codequality-config/checkstyle/intellij-java-google-style.xml`
