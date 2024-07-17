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

    @Autowired
    private WCAApi wcaApi;

    @Override
    public UserInfoDTO getUserInfo(String token) {
        return wcaApi.getUserInfo(token);
    }
}
