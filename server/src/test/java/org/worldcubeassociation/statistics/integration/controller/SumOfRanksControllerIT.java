package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import static io.restassured.RestAssured.given;

@DisplayName("Sum of Ranks")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/SumOfRanksControllerIT.sql"})
public class SumOfRanksControllerIT extends AbstractTest {
    private static final String BASE_PATH = "/sum-of-ranks/";

    @Test
    @Order(1)
    @DisplayName("List best ever rank")
    public void generate() {
        Response response = given()
                .spec(super.SPEC)
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        super.validateResponse(0, response);
    }

}
