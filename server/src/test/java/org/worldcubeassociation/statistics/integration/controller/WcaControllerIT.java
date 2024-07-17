package org.worldcubeassociation.statistics.integration.controller;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.worldcubeassociation.statistics.integration.AbstractTest;

@DisplayName("WCA")
public class WcaControllerIT extends AbstractTest {

    private static final String BASE_PATH = "/wca/";

    @DisplayName("WCA user info")
    @MethodSource("userInfoArguments")
    @ParameterizedTest(name = "index {0} status {1} token {2} reason {3}")
    public void userInfo(int index, HttpStatus status, String token, String reason) {
        Response response = given()
            .spec(super.SPEC)
            .header("Authorization", token)
            .when()
            .get(BASE_PATH + "user")
            .then()
            .statusCode(status.value())
            .extract()
            .response();

        super.validateResponse(index, response);
    }

    private static Stream<Arguments> userInfoArguments() {
        return Stream.of(
            Arguments.of(0, HttpStatus.OK, "Bearer token", "Happy path"),
            Arguments.of(1, HttpStatus.UNAUTHORIZED, "", "Unauthorized")
        );
    }
}
