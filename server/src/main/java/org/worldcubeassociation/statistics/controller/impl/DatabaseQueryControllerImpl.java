package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.DatabaseQueryController;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;

@RestController
public class DatabaseQueryControllerImpl implements DatabaseQueryController {

    @Autowired
    private DatabaseQueryService databaseQueryService;

    public DatabaseQueryDTO getResultSet(String sqlQuery, Integer page, Integer size)
            throws InvalidParameterException {
        return databaseQueryService.getResultSet(sqlQuery, page, size);
    }
}
