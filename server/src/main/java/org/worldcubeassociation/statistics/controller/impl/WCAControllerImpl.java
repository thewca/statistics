package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.WCAController;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.response.AuthenticationResponse;
import org.worldcubeassociation.statistics.service.WCAService;

@RestController
public class WCAControllerImpl implements WCAController {

    @Autowired
    private WCAService wcaService;

    @Override
    public AuthenticationResponse getWcaAuthenticationUrl(String frontendHost) {
        return wcaService.getWcaAuthenticationUrl(frontendHost);
    }

    @Override
    public UserInfoDTO getUserInfo(String token) {
        return wcaService.getUserInfo(token);
    }
}
