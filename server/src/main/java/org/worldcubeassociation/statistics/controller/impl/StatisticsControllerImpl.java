package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsController;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

@RestController
public class StatisticsControllerImpl implements StatisticsController {
    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) {
        return new StatisticsResponseDTO();
    }
}
