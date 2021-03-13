package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.vo.DatabaseQueryVo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RequestMapping("database")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface DatabaseQueryController {

    @GetMapping("query")
    DatabaseQueryVo getResultSet(@RequestParam @NotBlank String sqlQuery,
                                 @RequestParam(defaultValue = "0") @Min(0) Integer page,
                                 @RequestParam(defaultValue = "25") @Max(100) @Min(1) Integer size)
            throws InvalidParameterException;
}
