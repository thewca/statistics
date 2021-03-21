package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;
import org.worldcubeassociation.statistics.service.StatisticsService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private DatabaseQueryService databaseQueryService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) throws IOException {
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
                statisticsGroupResponseDTO.setKeys(query.getKeys());
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

        String path = String.join("-",
                StringUtils.stripAccents(statisticsRequestDTO.getTitle().replaceAll("[^a-zA-Z0-9 ]", "")).split(" "))
                .toLowerCase();
        ;
        statisticsResponseDTO.setPath(path);

        createLocalFile(statisticsResponseDTO, path);

        updateControlList();

        return statisticsResponseDTO;
    }

    @Override
    public void generateAll() throws IOException {
        log.info("Find all statistics");

        File folder = ResourceUtils.getFile("classpath:statistics-request-list");

        List<File> files = Arrays.asList(folder.listFiles());
        log.info("Found {} statistics to generate", files.size());

        for (File file : files) {
            StatisticsRequestDTO request = MAPPER.readValue(file, StatisticsRequestDTO.class);
            log.info("Statistic {}", request.getTitle());

            sqlToStatistics(request);
        }


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
            if (queries.size() != queries.stream().map(StatisticsGroupRequestDTO::getKeys).distinct().count()) {
                throw new InvalidParameterException("The identifier keys must be unique.");
            }
        }

        log.info("Validated");
    }

    private void createLocalFile(StatisticsResponseDTO statisticsResponseDTO, String path) throws IOException {
        log.info("Create local file");
        String fileName = String.format("statistics-list/%s.json", path);
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        file.createNewFile();
        MAPPER.writeValue(file, statisticsResponseDTO);
        log.info("Local file created");
    }

    private void updateControlList() throws IOException {
        log.info("Update control list");

        String controlListFileName = "statistics-list/_control-list_.json";
        File controlFile = new File(controlListFileName);

        List<ControlItemDTO> controlList = new ArrayList<>();

        File folder = controlFile.getParentFile();
        List<String> statistics =
                Arrays.stream(folder.list()).filter(name -> !controlListFileName.endsWith(name)).collect(
                        Collectors.toList());

        for (String fileName : statistics) {
            File file = new File("statistics-list/" + fileName);
            StatisticsResponseDTO stat = MAPPER.readValue(file, StatisticsResponseDTO.class);

            ControlItemDTO controlItemDTO = new ControlItemDTO();
            controlItemDTO.setPath(stat.getPath());
            controlItemDTO.setTitle(stat.getTitle());

            controlList.add(controlItemDTO);
        }

        Collections.sort(controlList, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));

        MAPPER.writeValue(controlFile, controlList);

        log.info("List updated");

    }
}
