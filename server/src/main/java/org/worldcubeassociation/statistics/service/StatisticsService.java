package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsListDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

import java.io.IOException;
import java.time.LocalDate;
import javax.validation.Valid;

public interface StatisticsService {
    StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException;

    void generateAllFromSql() throws IOException;

    void generateFromSql(String filename) throws IOException;

    StatisticsListDTO list(String term);

    StatisticsResponseDTO getStatistic(String pathId);

    StatisticsResponseDTO create(@Valid StatisticsDTO statisticsDTO);

    void deleteAll();

    LocalDate getExportDate();
}
