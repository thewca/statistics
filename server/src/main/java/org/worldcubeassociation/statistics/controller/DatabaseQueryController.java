package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.vo.DatabaseQueryVo;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;

@RequestMapping("database")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface DatabaseQueryController {

    @GetMapping("query")
    DatabaseQueryVo getResultSet(String sqlQuery, Integer page, Integer size)
            throws InvalidParameterException;
}
