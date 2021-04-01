package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.StatisticsController;
import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.service.StatisticsService;

import java.io.IOException;
import java.util.List;
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
    public List<ControlItemDTO> list() throws IOException {
        return statisticsService.list();
    }

    @Override
    public StatisticsResponseDTO getStatistic(String pathId) throws IOException {
        return statisticsService.getStatistic(pathId);
    }

    @Override
    public StatisticsResponseDTO createStatistics(@Valid StatisticsDTO statisticsDTO)
            throws IOException {
        return statisticsService.create(statisticsDTO);
    }

    @Override
    public void deleteAll() throws IOException {
        statisticsService.deleteAll();
    }
}
