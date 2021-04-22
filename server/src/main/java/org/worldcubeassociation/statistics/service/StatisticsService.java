package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;

public interface StatisticsService {
    StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException;

    void generateAllFromSql() throws IOException;

    void generateFromSql(String pathId) throws IOException;

    List<ControlItemDTO> list() throws IOException;

    StatisticsResponseDTO getStatistic(String pathId) throws IOException;

    StatisticsResponseDTO create(@Valid StatisticsDTO statisticsDTO) throws IOException;

    void deleteAll() throws IOException;
}
