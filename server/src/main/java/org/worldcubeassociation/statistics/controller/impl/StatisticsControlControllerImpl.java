package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsControlController;
import org.worldcubeassociation.statistics.service.AuthorizationService;
import org.worldcubeassociation.statistics.service.StatisticsControlService;

@RestController
public class StatisticsControlControllerImpl implements StatisticsControlController {
    private final StatisticsControlService service;
    private final AuthorizationService authorizationService;

    public StatisticsControlControllerImpl(StatisticsControlService service,
        AuthorizationService authorizationService) {
        this.service = service;
        this.authorizationService = authorizationService;
    }

    @Override
    public void start(String path) {
        authorizationService.disableInProd();
        service.start(path);
    }

    @Override
    public void complete(String path) {
        authorizationService.disableInProd();
        service.complete(path);
    }

    @Override
    public void error(String path, String errorMessage) {
        authorizationService.disableInProd();
        service.error(path, errorMessage);
    }
}
