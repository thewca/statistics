package org.worldcubeassociation.statistics.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.api.WCAApi;
import org.worldcubeassociation.statistics.dto.UserInfoDTO;
import org.worldcubeassociation.statistics.exception.UnauthorizedException;
import org.worldcubeassociation.statistics.service.AuthorizationService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private static Map<String, LocalDateTime> cachedTokens = new TreeMap<>();

    @Autowired
    private WCAApi wcaApi;

    @Value("${service.authorization.token-valid-for}")
    private int tokenValidFor;

    @Override
    public void isLoggedInWca(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            throw new UnauthorizedException("No token provided");
        }

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiresIn = cachedTokens.get(accessToken);
        if (expiresIn != null) {
            // User expired
            if (now.isAfter(expiresIn)) {
                cachedTokens.remove(accessToken);
                throw new UnauthorizedException("User expired");
            }

            // User is logged
            return;
        } else {
            UserInfoDTO user = wcaApi.getUserInfo(accessToken);

            // USer is validated and it's added
            if (user != null) {
                cachedTokens.put(accessToken, now.plusHours(tokenValidFor));
                return;
            }

            throw new UnauthorizedException("User not authorized");
        }


    }
}
