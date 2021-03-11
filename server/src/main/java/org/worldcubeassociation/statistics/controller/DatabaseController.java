package org.worldcubeassociation.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.service.DatabaseService;

@RestController
@RequestMapping("database")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("query")
    public ResultSetResponse getResultSet(String sqlQuery, Integer page, Integer size)
            throws InvalidParameterException {
        return databaseService.getResultSet(sqlQuery, page, size);
    }
}
