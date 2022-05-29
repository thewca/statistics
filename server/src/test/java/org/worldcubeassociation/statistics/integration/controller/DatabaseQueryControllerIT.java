package org.worldcubeassociation.statistics.integration.controller;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractTest;

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Database query")
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/BestEverRanksControllerIT.sql"})
public class DatabaseQueryControllerIT extends AbstractTest {
    private static final String BASE_PATH = "/database/";

    @MethodSource("queryArguments")
    @ParameterizedTest(name = "{displayName} {0}: status {1} token {2} body {3} reason {4}")
    public void query(int index, HttpStatus status, String token, Map<String, Object> body, String reason) {
        Response response = given()
                .spec(super.SPEC)
                .header("Authorization", token)
                .body(body)
                .when()
                .post(BASE_PATH + "query")
                .then()
                .statusCode(status.value())
                .extract()
                .response();

        super.validateResponse(index, response);
    }

    private static Stream<Arguments> queryArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "Bearer token", Map.of("sqlQuery", "select c.id competition_name, count(1) events from Competitions c inner join competition_events ce on c.id = ce.competition_id group by c.id"), "Happy path"),
                Arguments.of(1, HttpStatus.BAD_REQUEST, "Bearer token", Map.of("sqlQuery", "celect * from Competitions"), "Querry with error"),
                Arguments.of(2, HttpStatus.OK, "Bearer token", Map.of("page", 0, "size", 5, "sqlQuery", "select * from Events order by `rank`"), "Pagination 0"),
                Arguments.of(3, HttpStatus.OK, "Bearer token", Map.of("page", 1, "size", 5, "sqlQuery", "select * from Events order by `rank`"), "Pagination 1"),
                Arguments.of(4, HttpStatus.UNAUTHORIZED, "", Map.of("sqlQuery", "select * from Events order by `rank`"), "Unauthorized")
        );
    }
}
