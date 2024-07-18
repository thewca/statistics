package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.UserInfoDTO;

public interface WCAService {

    UserInfoDTO getUserInfo(String token);
}
