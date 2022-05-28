package org.worldcubeassociation.statistics.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;
import org.worldcubeassociation.statistics.response.DatabaseQueryMetaResponse;

import javax.validation.Valid;

@RequestMapping("database")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface DatabaseQueryController {

    @PostMapping("query")
    // Queries can't take more than 2 min to run
    @Transactional(timeout = 120)
    DatabaseQueryDTO getResultSet(@Valid @RequestBody DatabaseQueryRequest databaseQueryRequest,
                                  @RequestHeader(value = "Authorization", required = false) String accessToken);

    @GetMapping("meta")
    DatabaseQueryMetaResponse meta(@RequestHeader(value = "Authorization", required = false) String accessToken);

}
