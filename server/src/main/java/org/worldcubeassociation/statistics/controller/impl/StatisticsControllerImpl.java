package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsController;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsListDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.service.StatisticsService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class StatisticsControllerImpl implements StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Value("${spring.profiles.active}")
    private String profile;

    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException {
        validateProfile();
        return statisticsService.sqlToStatistics(statisticsRequestDTO);
    }

    @Override
    public void generateAllFromSql() throws IOException {
        validateProfile();
        statisticsService.generateAllFromSql();
    }

    @Override
    public void generateFromSql(String filename) throws IOException {
        validateProfile();
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
        validateProfile();
        return statisticsService.create(statisticsDTO);
    }

    @Override
    public void deleteAll() {
        validateProfile();
        statisticsService.deleteAll();
    }

    private void validateProfile() {
        if ("prod".equals(profile)) {
            throw new InvalidParameterException("This endpoint is not allowed in the environment " + profile);
        }
    }
}
