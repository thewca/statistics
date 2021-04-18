package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.DatabaseQueryController;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;

@RestController
public class DatabaseQueryControllerImpl implements DatabaseQueryController {

    @Autowired
    private DatabaseQueryService databaseQueryService;

    public DatabaseQueryDTO getResultSet(DatabaseQueryRequest databaseQueryRequest) {
        return databaseQueryService.getResultSet(databaseQueryRequest);
    }
}
