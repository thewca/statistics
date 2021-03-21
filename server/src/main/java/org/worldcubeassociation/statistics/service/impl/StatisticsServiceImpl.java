package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupRequestDTO;
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
        log.info("SQL to statistics for {}", statisticsRequestDTO);

        validateRequest(statisticsRequestDTO);

        StatisticsResponseDTO statisticsResponseDTO = new StatisticsResponseDTO();
        statisticsResponseDTO.setStatistics(new ArrayList<>());

        String sqlQuery = statisticsRequestDTO.getSqlQuery();
        if (!StringUtils.isEmpty(sqlQuery)) {
            log.info("Single query mode");

            DatabaseQueryBaseDTO sqlResult = databaseQueryService.getResultSet(sqlQuery);

            StatisticsGroupResponseDTO statisticsGroupResponseDTO = new StatisticsGroupResponseDTO();
            statisticsGroupResponseDTO.setContent(sqlResult.getContent());
            statisticsResponseDTO.getStatistics().add(statisticsGroupResponseDTO);

            statisticsResponseDTO
                    .setHeaders(Optional.ofNullable(statisticsRequestDTO.getHeaders()).orElse(sqlResult.getHeaders()));

            // Setting display mode null since the user might have made a mistake
            statisticsRequestDTO.setDisplayMode(null);
        } else {
            log.info("Multiple query mode");

            Integer headersCount = null;

            for (StatisticsGroupRequestDTO query : statisticsRequestDTO.getSqlQueries()) {
                DatabaseQueryBaseDTO sqlResult = databaseQueryService.getResultSet(query.getSqlQuery());

                StatisticsGroupResponseDTO statisticsGroupResponseDTO = new StatisticsGroupResponseDTO();
                statisticsGroupResponseDTO.setKey(query.getKey());
                statisticsGroupResponseDTO.setContent(sqlResult.getContent());
                statisticsResponseDTO.getStatistics().add(statisticsGroupResponseDTO);
                statisticsResponseDTO.setHeaders(
                        Optional.ofNullable(statisticsRequestDTO.getHeaders()).orElse(sqlResult.getHeaders()));

                if (headersCount != null && statisticsResponseDTO.getHeaders().size() != headersCount) {
                    throw new InvalidParameterException("The number of headers must match across all queries");
                }

                if (statisticsRequestDTO.getHeaders() != null
                        && statisticsResponseDTO.getHeaders().size() != sqlResult.getHeaders().size()) {
                    throw new InvalidParameterException(
                            "The provided headers length and the response headers length must match. If you are "
                                    + "unsure, leave it empty.");
                }
            }
        }

        statisticsResponseDTO
                .setDisplayMode(Optional.ofNullable(statisticsRequestDTO.getDisplayMode()).orElse("DEFAULT"));
        statisticsResponseDTO.setExplanation(statisticsRequestDTO.getExplanation());
        statisticsResponseDTO.setTitle(statisticsRequestDTO.getTitle());

        return statisticsResponseDTO;
    }

    private void validateRequest(StatisticsRequestDTO statisticsRequestDTO) {
        boolean isQueryEmpty = StringUtils.isEmpty(statisticsRequestDTO.getSqlQuery());
        int numberOfQueries = Optional.ofNullable(statisticsRequestDTO.getSqlQueries()).map(List::size).orElse(0);

        if (isQueryEmpty && numberOfQueries == 0) {
            throw new InvalidParameterException("No SQL query informed.");
        }

        if (!isQueryEmpty && numberOfQueries > 0) {
            throw new InvalidParameterException("Please provide either a query or a set of queries, but not both.");
        }

        if (numberOfQueries == 1) {
            throw new InvalidParameterException(
                    "Please use the sqlQuery field if your intention is to provide only 1 query.");
        }

        if (numberOfQueries > 0) {
            final List<StatisticsGroupRequestDTO> queries = statisticsRequestDTO.getSqlQueries();

            queries.forEach(query -> {
                if (query == null) {
                    throw new InvalidParameterException("Query item must not be null.");
                }

                if (StringUtils.isEmpty(query.getSqlQuery())) {
                    throw new InvalidParameterException("One of the provided queries is empty.");
                }

            });

            // Key emptiness validation already happens in the bean, so we can validate just if keys are unique
            if (queries.size() != queries.stream().map(StatisticsGroupRequestDTO::getKey).distinct().count()) {
                throw new InvalidParameterException("The identifier keys must be unique.");
            }
        }

        log.info("Validated");
    }
}
