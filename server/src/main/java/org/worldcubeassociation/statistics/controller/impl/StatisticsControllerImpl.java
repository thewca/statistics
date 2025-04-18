package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsController;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsListDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.service.AuthorizationService;
import org.worldcubeassociation.statistics.service.StatisticsService;

import jakarta.validation.Valid;
import java.io.IOException;

@RestController
public class StatisticsControllerImpl implements StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private AuthorizationService authorizationService;

    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException {
        authorizationService.disableInProd();
        return statisticsService.sqlToStatistics(statisticsRequestDTO);
    }

    @Override
    public void generateAllFromSql() throws IOException {
        authorizationService.disableInProd();
        statisticsService.generateAllFromSql();
    }

    @Override
    public void generateFromSql(String filename) throws IOException {
        authorizationService.disableInProd();
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
        authorizationService.disableInProd();
        return statisticsService.create(statisticsDTO);
    }

    @Override
    public void deleteAll() {
        authorizationService.disableInProd();
        statisticsService.deleteAll();
    }
}
