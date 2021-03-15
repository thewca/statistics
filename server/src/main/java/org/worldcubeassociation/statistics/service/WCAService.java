package org.worldcubeassociation.statistics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;

public interface WCAService {
    String getWcaAuthenticationUrl(String frontendHost);

    UserInfoDTO getUserInfo(String accessToken, String tokenType) throws JsonProcessingException;
}
