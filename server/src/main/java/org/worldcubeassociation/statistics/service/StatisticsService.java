package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;

import java.io.IOException;
import java.util.List;

public interface StatisticsService {
    StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException;

    void generateAll() throws IOException;

    List<ControlItemDTO> list() throws IOException;
}
