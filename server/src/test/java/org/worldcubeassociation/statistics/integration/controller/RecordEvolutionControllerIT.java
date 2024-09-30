package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Database query")
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/RecordEvolutionControllerIT.sql"})
public class RecordEvolutionControllerIT extends AbstractTest {
    private static final String BASE_PATH = "record-evolution/";

    @Test
    public void getAvailableEvents() {
        Response response = given()
                .spec(super.createRequestSpecification())
                .when()
                .get(BASE_PATH + "event")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        super.validateResponse(0, response);
    }

    @MethodSource("getByEventArguments")
    @DisplayName("Test statistics generated from yaml files")
    @ParameterizedTest(name = "{displayName} {0}: status {1} reason {2}")
    public void getByEvent(int index, HttpStatus status, String event) {
        Response response = given()
                .spec(super.createRequestSpecification())
                .when()
                .get(BASE_PATH + event)
                .then()
                .statusCode(status.value())
                .extract()
                .response();

        super.validateResponse(index, response);
    }

    private static Stream<Arguments> getByEventArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "444bf"),
                Arguments.of(1, HttpStatus.OK, "555bf"),
                Arguments.of(2, HttpStatus.NOT_FOUND, "333notfound")
        );
    }

}
