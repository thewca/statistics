package org.worldcubeassociation.statistics.service;

@FunctionalInterface
public interface AuthorizationService {
    void isLoggedInWca(String accessToken);
}
