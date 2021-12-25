FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=build/libs/statistics-server.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
