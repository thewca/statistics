package org.worldcubeassociation.statistics.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.dto.UserInfoWrapperDTO;

@Slf4j
@Component
public class WCAApi {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Value("${api.wca.baseurl}")
    private String wcaBaseUrl;

    public UserInfoDTO getUserInfo(String accessToken) {
        log.info("Get user info");

        String url = wcaBaseUrl + "/api/v0/me";
        log.info("url = {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<UserInfoWrapperDTO> response =
                REST_TEMPLATE.exchange(url, HttpMethod.GET, entity, UserInfoWrapperDTO.class);
        return response.getBody().getMe();
    }

    public UserInfoDTO getUserInfo(String accessToken, String tokenTyke) {
        return getUserInfo(String.format("%s %s", tokenTyke, accessToken));
    }
}
