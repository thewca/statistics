package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.AuthenticationController;

@RestController
public class AuthenticationControllerImpl implements AuthenticationController {
    @Value("${api.wca.baseurl}")
    private String wcaBaseUrl;

    @Value("${api.wca.appid}")
    private String wcaAppId;

    @Override
    public String getWcaAuthenticationUrl(String frontendHost) {
        return String
                .format("%s/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=token&scope=public", wcaBaseUrl,
                        wcaAppId, frontendHost);
    }
}
