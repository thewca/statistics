package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

import java.io.IOException;
import javax.validation.Valid;

@RequestMapping("statistics")
public interface StatisticsController {
    @PostMapping
    StatisticsResponseDTO sqlToStatistics(@Valid @RequestBody StatisticsRequestDTO statisticsRequestDTO)
            throws IOException;

    @PostMapping("generate")
    void generateAll() throws IOException;

}
