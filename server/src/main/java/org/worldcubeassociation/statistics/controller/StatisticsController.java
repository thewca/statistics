package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

@RequestMapping("statistic")
public interface StatisticsController {
    @PostMapping
    StatisticsResponseDTO sqlToStatistics(@RequestBody StatisticsRequestDTO statisticsRequestDTO);
}
