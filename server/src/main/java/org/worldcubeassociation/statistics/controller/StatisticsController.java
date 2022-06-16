package org.worldcubeassociation.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsListDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

import java.io.IOException;
import javax.validation.Valid;

@RequestMapping("statistics")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface StatisticsController {

    @PostMapping
    @Operation(summary =
            "Mostly for test. You can create a statistic from an SQL without the need to generate all the statistics."
                    + " On it's simplest form, you can get the content of a JSON in resources/statistics-request-list"
                    + " and use it as body.")
    StatisticsResponseDTO sqlToStatistics(@Valid @RequestBody StatisticsRequestDTO statisticsRequestDTO)
            throws IOException;

    @PostMapping("generate-from-sql")
    @Operation(summary = "Convert all sql queries in the resource folder to statistics")
    void generateAllFromSql() throws IOException;

    @PostMapping("generate-from-sql/{filename}")
    @Operation(summary = "Convert specific sql queries in the resource folder to statistics")
    void generateFromSql(@PathVariable String filename) throws IOException;

    @GetMapping("list")
    StatisticsListDTO list(String term);

    @GetMapping("list/{pathId}")
    StatisticsResponseDTO getStatistic(@PathVariable String pathId);

    @PostMapping("create")
    @Operation(summary =
            "This method allows you to create a new statistics in any language you like. Just post a valid payload "
                    + "and it will be available.")
    StatisticsResponseDTO createStatistics(@Valid @RequestBody StatisticsDTO statisticsDTO);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes all generated statistics")
    void deleteAll();
}
