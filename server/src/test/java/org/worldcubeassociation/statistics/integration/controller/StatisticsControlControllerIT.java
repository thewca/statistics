package org.worldcubeassociation.statistics.integration.controller;

import static io.restassured.RestAssured.given;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.worldcubeassociation.statistics.integration.AbstractIT;

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
}
