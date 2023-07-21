package org.worldcubeassociation.statistics.controller.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.WcaController;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.exception.UnauthorizedException;
import org.worldcubeassociation.statistics.response.AuthenticationResponse;
import org.worldcubeassociation.statistics.service.WCAService;

@RestController
public class WcaControllerImpl implements WcaController {

    @Autowired
    private WCAService wcaService;

    @Override
    public AuthenticationResponse getWcaAuthenticationUrl(String frontendHost, String redirect) {
        return wcaService.getWcaAuthenticationUrl(frontendHost, redirect);
    }

    @Override
    public UserInfoDTO getUserInfo(String token) {
        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException("User not authenticated");
        }
        return wcaService.getUserInfo(token);
    }
}
