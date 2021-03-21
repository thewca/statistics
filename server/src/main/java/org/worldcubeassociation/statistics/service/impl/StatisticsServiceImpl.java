package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;
import org.worldcubeassociation.statistics.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private DatabaseQueryService databaseQueryService;

    @Override
    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) {
        validate(statisticsRequestDTO);

        StatisticsResponseDTO statisticsResponseDTO = new StatisticsResponseDTO();
        statisticsResponseDTO.setStatistics(new ArrayList<>());

        String query = statisticsRequestDTO.getSqlQuery();
        if (!StringUtils.isEmpty(query)) {
            DatabaseQueryBaseDTO sqlResult = databaseQueryService.getResultSet(query);

            StatisticsGroupResponseDTO statisticsGroupResponseDTO = new StatisticsGroupResponseDTO();
            statisticsGroupResponseDTO.setContent(sqlResult.getContent());
            statisticsResponseDTO.getStatistics().add(statisticsGroupResponseDTO);

            statisticsResponseDTO
                    .setHeaders(Optional.ofNullable(statisticsRequestDTO.getHeaders()).orElse(sqlResult.getHeaders()));
        } else {

        }

        statisticsResponseDTO.setDisplayMode(statisticsRequestDTO.getDisplayMode());
        statisticsResponseDTO.setExplanation(statisticsRequestDTO.getExplanation());
        statisticsResponseDTO.setTitle(statisticsRequestDTO.getTitle());

        return statisticsResponseDTO;
    }

    private void validate(StatisticsRequestDTO statisticsRequestDTO) {
        boolean isQueryEmpty = StringUtils.isEmpty(statisticsRequestDTO.getSqlQuery());
        int numberOfQueries = Optional.ofNullable(statisticsRequestDTO.getSqlQueries()).map(List::size).orElse(0);

        if (isQueryEmpty && numberOfQueries == 0) {
            throw new InvalidParameterException("No SQL query informed.");
        }

        if (!isQueryEmpty && numberOfQueries > 0) {
            throw new InvalidParameterException("Please provide either a query or a set of queries, but not both.");
        }

        if (numberOfQueries > 0){
            statisticsRequestDTO.getSqlQueries().forEach(query -> {
                if (StringUtils.isEmpty(query)){
                    throw new InvalidParameterException("One of the provided queries is empty");
                }
            });
        }
    }
}
