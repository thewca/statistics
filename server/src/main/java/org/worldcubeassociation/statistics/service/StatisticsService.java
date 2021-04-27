package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.model.Statistics;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;

public interface StatisticsService {
    StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException;

    void generateAllFromSql() throws IOException;

    void generateFromSql(String filename) throws IOException;

    List<StatisticsGroupDTO> list() throws IOException;

    StatisticsDTO getStatistic(String pathId) throws IOException;

    StatisticsResponseDTO create(@Valid StatisticsDTO statisticsDTO) throws IOException;

    void deleteAll() throws IOException;

    List<Statistics> findAll();
}
