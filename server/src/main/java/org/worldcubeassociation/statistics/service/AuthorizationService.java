package org.worldcubeassociation.statistics.service;

public interface AuthorizationService {
    void isLoggedInWca(String accessToken);

    void disableInProd();
}
