package org.worldcubeassociation.statistics.integration.controller;

import static io.restassured.RestAssured.given;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractIT;

@DisplayName("Statistics control")
@Sql({
    "/test-scripts/cleanTestData.sql",
    "/test-scripts/StatisticsControlControllerIT.sql"
})
class StatisticsControlControllerIT extends AbstractIT {

    private static final String BASE_PATH = "/statistics-control";

    @MethodSource("startArguments")
    @ParameterizedTest(name = "{displayName} {0}: status {1} file {2}")
    @DisplayName("Test statistics generated from specific yaml file")
    void start(int index, HttpStatus status, String path) {
        var response = given()
            .spec(super.createRequestSpecification())
            .when()
            .queryParam("path", path)
            .post(BASE_PATH)
            .then()
            .statusCode(status.value())
            .extract()
            .response();

        if (status != HttpStatus.OK) {
            super.validateResponse(index, response);
        }
    }

    private static Stream<Arguments> startArguments() {
        return Stream.of(
            Arguments.of(0, HttpStatus.OK, "new_stat.py", "Happy path"),
            Arguments.of(1, HttpStatus.BAD_REQUEST, null, "Null path")
        );
    }

    @MethodSource("completeArguments")
    @ParameterizedTest(name = "{displayName} {0}: status {1} file {2}")
    @DisplayName("Test statistics generated from specific yaml file")
    void complete(int index, HttpStatus status, String path) {
        var response = given()
            .spec(super.createRequestSpecification())
            .when()
            .queryParam("path", path)
            .patch(BASE_PATH)
            .then()
            .statusCode(status.value())
            .extract()
            .response();

        if (status != HttpStatus.OK) {
            super.validateResponse(index, response);
        }
    }

    private static Stream<Arguments> completeArguments() {
        return Stream.of(
            Arguments.of(0, HttpStatus.OK, "existing_stat.py", "Happy path"),
            Arguments.of(1, HttpStatus.BAD_REQUEST, null, "Null path"),
            Arguments.of(2, HttpStatus.NOT_FOUND, "not_found.py", "Non existing path")
        );
    }

    @MethodSource("errorArguments")
    @ParameterizedTest(name = "{displayName} {0}: status {1} path {2} message {3}")
    @DisplayName("Test statistics generated from specific yaml file")
    void error(int index, HttpStatus status, String path, String message) {
        var response = given()
            .spec(super.createRequestSpecification())
            .when()
            .queryParam("path", path)
            .queryParam("message", message)
            .post(BASE_PATH + "/error")
            .then()
            .statusCode(status.value())
            .extract()
            .response();

        if (status != HttpStatus.OK) {
            super.validateResponse(index, response);
        }
    }

    private static Stream<Arguments> errorArguments() {
        return Stream.of(
            Arguments.of(0, HttpStatus.OK, "existing_stat.py", "Some exception", "Happy path"),
            Arguments.of(1, HttpStatus.BAD_REQUEST, null, "Some exception", "Null path"),
            Arguments.of(2, HttpStatus.NOT_FOUND, "not_found.py", "Non existing path"),
            Arguments.of(3, HttpStatus.OK, "existing_stat.py", null, "Null messages allowed")
        );
    }
}
