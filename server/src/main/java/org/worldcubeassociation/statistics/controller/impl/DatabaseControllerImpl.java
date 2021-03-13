package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.DatabaseController;
import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.service.DatabaseService;

@RestController
public class DatabaseControllerImpl implements DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    public ResultSetResponse getResultSet(String sqlQuery, Integer page, Integer size)
            throws InvalidParameterException {
        return databaseService.getResultSet(sqlQuery, page, size);
    }
}
