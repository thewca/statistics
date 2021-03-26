package org.worldcubeassociation.statistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.api.WCAApi;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.service.WCAService;

@Service
public class WCAServiceImpl implements WCAService {
    @Value("${api.wca.baseurl}")
    private String wcaBaseUrl;

    @Value("${api.wca.appid}")
    private String wcaAppId;

    @Autowired
    private WCAApi wcaApi;

    @Override
    public String getWcaAuthenticationUrl(String frontendHost) {
        return String
                .format("%s/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=token&scope=public", wcaBaseUrl,
                        wcaAppId, frontendHost);
    }

    @Override
    public UserInfoDTO getUserInfo(String accessToken, String tokenType) {
        return wcaApi.getUserInfo(accessToken, tokenType);
    }

}
