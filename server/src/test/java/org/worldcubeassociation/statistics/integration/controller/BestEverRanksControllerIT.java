package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/BestEverRanksControllerIT.sql"})
public class BestEverRanksControllerIT extends AbstractTest {
    private static final String BASE_PATH = "/best-ever-rank/";

    @MethodSource("autocompleteArguments")
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

    static Stream<Arguments> autocompleteArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "2015CAMP17", "Happy path for best ever rank"),
                Arguments.of(1, HttpStatus.NOT_FOUND, "2022XXXX01", "User not found")
        );
    }
}
