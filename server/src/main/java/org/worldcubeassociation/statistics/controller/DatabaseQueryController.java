package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RequestMapping("database")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface DatabaseQueryController {

    @PostMapping("query")
    DatabaseQueryDTO getResultSet(@RequestParam @NotBlank String sqlQuery,
                                  @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                  @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size);
}
