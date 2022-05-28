package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import static io.restassured.RestAssured.given;

@DisplayName("Database query")
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/RecordEvolutionControllerIT.sql"})
public class RecordEvolutionControllerIT extends AbstractTest {
    private static final String BASE_PATH = "record-evolution/";

    @Test
    public void getAvailableEvents() {
        Response response = given()
                .spec(super.SPEC)
                .when()
                .get(BASE_PATH + "event")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        super.validateResponse(0, response);
    }

}
