package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsController;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsListDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.service.StatisticsService;

import java.io.IOException;
import javax.validation.Valid;

@RestController
public class StatisticsControllerImpl implements StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException {
        return statisticsService.sqlToStatistics(statisticsRequestDTO);
    }

    @Override
    public void generateAllFromSql() throws IOException {
        statisticsService.generateAllFromSql();
    }

    @Override
    public void generateFromSql(String filename) throws IOException {
        statisticsService.generateFromSql(filename);
    }

    @Override
    public StatisticsListDTO list(String term) {
        return statisticsService.list(term);
    }

    @Override
    public StatisticsResponseDTO getStatistic(String pathId) {
        return statisticsService.getStatistic(pathId);
    }

    @Override
    public StatisticsResponseDTO createStatistics(@Valid StatisticsDTO statisticsDTO) {
        return statisticsService.create(statisticsDTO);
    }

    @Override
    public void deleteAll() {
        statisticsService.deleteAll();
    }
}
