package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.DatabaseQueryController;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;
import org.worldcubeassociation.statistics.response.DatabaseQueryMetaResponse;
import org.worldcubeassociation.statistics.service.AuthorizationService;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;

@RestController
public class DatabaseQueryControllerImpl implements DatabaseQueryController {

    @Autowired
    private DatabaseQueryService databaseQueryService;

    @Autowired
    private AuthorizationService authorizationService;

    public DatabaseQueryDTO getResultSet(DatabaseQueryRequest databaseQueryRequest, String accessToken) {
        authorizationService.isLoggedInWca(accessToken);

        return databaseQueryService.getResultSet(databaseQueryRequest, accessToken);
    }

    @Override
    public DatabaseQueryMetaResponse meta(DatabaseQueryRequest databaseQueryRequest, String accessToken) {
        authorizationService.isLoggedInWca(accessToken);

        return databaseQueryService.meta(databaseQueryRequest, accessToken);
    }
}
