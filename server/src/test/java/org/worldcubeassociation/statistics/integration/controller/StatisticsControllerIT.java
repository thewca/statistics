package org.worldcubeassociation.statistics.integration.controller;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import static io.restassured.RestAssured.given;

@DisplayName("Best ever rank")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/StatisticsControllerIT.sql"})
public class StatisticsControllerIT extends AbstractTest {
    private static final String BASE_PATH = "/statistics/";

    @Test
    @Order(1)
    @DisplayName("Test statistics generated from yaml files")
    public void list() {
        given()
                .spec(super.SPEC)
                .when()
                .post(BASE_PATH + "generate-from-sql")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
    }
}
