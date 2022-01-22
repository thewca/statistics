package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/BestEverRanksControllerIT.sql"})
public class BestEverRanksControllerIT extends AbstractTest {
    private static final String BASE_PATH = "/best-ever-rank/";

    @Order(1)
    @MethodSource("listArguments")
    @ParameterizedTest(name = "index {0} status {1} wcaId {2} reason {3}")
    public void list(int index, HttpStatus status, String wcaId, String reason) {
        Response response = given()
                .spec(super.SPEC)
                .when()
                .get(BASE_PATH + "{wca_id}", wcaId)
                .then()
                .statusCode(status.value())
                .extract()
                .response();

        super.validateResponseIgnoreAttribute(index, response, "timestamp");
    }

    static Stream<Arguments> listArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "2015CAMP17", "Happy path for best ever rank"),
                Arguments.of(1, HttpStatus.NOT_FOUND, "2022XXXX01", "User not found")
        );
    }

    @Order(3)
    @MethodSource("generateAllArguments")
    @ParameterizedTest(name = "index {0} status {1} reason {2}")
    public void generateAll(int index, HttpStatus status, String reason) {
        Response response = given()
                .spec(super.SPEC)
                .when()
                .post(BASE_PATH + "generate/all")
                .then()
                .statusCode(status.value())
                .extract()
                .response();

        super.validateResponse(index, response);
    }

    static Stream<Arguments> generateAllArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "Happy path")
        );
    }
}
