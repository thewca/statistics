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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.worldcubeassociation.statistics.integration.AbstractIT;

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@DisplayName("Sum of Ranks")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql({"/test-scripts/cleanTestData.sql", "/test-scripts/SumOfRanksControllerIT.sql"})
public class SumOfRanksControllerIT extends AbstractIT {
    private static final String BASE_PATH = "/sum-of-ranks";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Response generateSor() {
        return given()
                .spec(super.createRequestSpecification())
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();
    }

    @Test
    @Order(1)
    @DisplayName("Generate sum of ranks")
    public void generate() {
        Response response = generateSor();
        super.validateResponse(0, response);
    }

    @Order(2)
    @MethodSource("listArguments")
    @DisplayName("List sum of ranks")
    @ParameterizedTest(
            name = "{displayName} {0}: status {1} result type {2} region type {3} region {4} params {5} reason {6}")
    public void list(int index, HttpStatus status, String resultType, String regionType, String region,
                     Map<String, Object> params, String reason) {
        // Test scripts clears database everytime
        generateSor();

        Response response = given()
                .spec(super.createRequestSpecification())
                .params(params)
                .when()
                .get(BASE_PATH + "/{resultType}/{regionType}/{region}", resultType, regionType, region)
                .then()
                .statusCode(status.value())
                .extract()
                .response();

        super.validateResponse(index, response);
    }

    static Stream<Arguments> listArguments() {
        return Stream.of(
                Arguments.of(0, HttpStatus.OK, "Single", "World", "World", Map.of("page", 0, "pageSize", 10),
                        "Pagination success"),
                Arguments.of(1, HttpStatus.OK, "Single", "World", "World", Map.of("page", 1, "pageSize", 6),
                        "Another pagination success"),
                Arguments.of(3, HttpStatus.OK, "Single", "World", "World",
                        Map.of("page", 0, "pageSize", 3, "wcaId", "2017SOUZ10"),
                        "Search by wca id"),
                Arguments.of(4, HttpStatus.OK, "Average", "Continent", "South America",
                        Map.of("page", 0, "pageSize", 3, "wcaId", "2017SOUZ10"),
                        "Search by wca id, continent"),
                Arguments.of(5, HttpStatus.OK, "Average", "Country", "Brazil",
                        Map.of("page", 0, "pageSize", 3, "wcaId", "2017SOUZ10"),
                        "Search by wca id, country"),
                Arguments.of(6, HttpStatus.BAD_REQUEST, "Average", "Country", "Brazil",
                        Map.of("page", 0, "pageSize", 3, "wcaId", "2015CAMP17"),
                        "User not found for region")
        );
    }

    @Test
    @Order(3)
    public void meta() {
        // Test scripts clears database everytime
        generateSor();

        Response response = given()
                .spec(super.createRequestSpecification())
                .when()
                .get(BASE_PATH + "/meta")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        super.validateResponse(0, response);
    }
}
