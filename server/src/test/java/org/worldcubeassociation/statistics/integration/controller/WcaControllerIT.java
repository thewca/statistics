package org.worldcubeassociation.statistics.integration.controller;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.worldcubeassociation.statistics.dto.UserInfoWrapperDTO;
import org.worldcubeassociation.statistics.integration.AbstractIT;
import org.worldcubeassociation.statistics.util.LoadResourceUtil;

@DisplayName("WCA")
public class WcaControllerIT extends AbstractIT {

    private static final String BASE_PATH = "/wca/";

    @MockBean
    private RestTemplate restTemplate;

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @DisplayName("WCA user info")
    @MethodSource("userInfoArguments")
    @ParameterizedTest(name = "index {0} status {1} token {2} reason {3}")
    public void userInfo(int index, HttpStatus status, String token, String reason)
        throws JsonProcessingException {
        var resource = LoadResourceUtil.getResource("mocks/userInfo.json");
        var userInfoWrapper = OBJECT_MAPPER.readValue(resource, UserInfoWrapperDTO.class);

        Mockito.when(
                restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                    any(Class.class)))
            .thenReturn(ResponseEntity.of(Optional.of(userInfoWrapper)));

        Response response = given()
            .spec(super.createRequestSpecification())
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
