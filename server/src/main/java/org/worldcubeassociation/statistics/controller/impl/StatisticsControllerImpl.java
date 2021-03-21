package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsController;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.service.StatisticsService;

import java.io.IOException;

@RestController
public class StatisticsControllerImpl implements StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException {
        return statisticsService.sqlToStatistics(statisticsRequestDTO);
    }

    @Override
    public void generateAll() throws IOException {
        statisticsService.generateAll();
    }
}
