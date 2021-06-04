package org.worldcubeassociation.statistics.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;

import javax.validation.Valid;

@RequestMapping("database")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface DatabaseQueryController {

    @PostMapping("query")
    // TODO this error is returning 500. It should be 400.
    // Queries can't take more than 2 min to run
    @Transactional(timeout = 120)
    DatabaseQueryDTO getResultSet(@Valid @RequestBody DatabaseQueryRequest databaseQueryRequest, @RequestHeader("Authorization") String token);
}
