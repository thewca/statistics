package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Best ever rank")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/StatisticsControllerIT.sql"})
public class StatisticsControllerIT extends AbstractTest {
    private static final String BASE_PATH = "/statistics/";

    @Test
    @Order(1)
    @DisplayName("Test statistics generated from yaml files")
    public void generateAll() {
        given()
                .spec(super.SPEC)
                .when()
                .post(BASE_PATH + "generate-from-sql")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Order(1)
    @MethodSource("generateFromFileArguments")
    @ParameterizedTest(name = "{displayName} {0}: status {1} file {2}")
    @DisplayName("Test statistics generated from specific yaml file")
    public void generateFromFile(int index, HttpStatus status, String fileName) {
        Response response = given()
                .spec(super.SPEC)
                .when()
                .post(BASE_PATH + "generate-from-sql/" + fileName)
                .then()
                .statusCode(status.value())
                .extract()
                .response();

        super.validateResponse(index, response);
    }

    private static Stream<Arguments> generateFromFileArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "most-persons", "Happy path"),
                Arguments.of(2, HttpStatus.NOT_FOUND, "file-not-found", "Not found")
        );
    }

    @Test
    @Order(3)
    @DisplayName("Test statistics generated from yaml files")
    public void deleteAll() {
        given()
                .spec(super.SPEC)
                .when()
                .delete(BASE_PATH)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
