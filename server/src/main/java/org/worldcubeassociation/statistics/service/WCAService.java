package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.response.AuthenticationResponse;

public interface WCAService {
    AuthenticationResponse getWcaAuthenticationUrl(String frontendHost);

    UserInfoDTO getUserInfo(String token);
}
