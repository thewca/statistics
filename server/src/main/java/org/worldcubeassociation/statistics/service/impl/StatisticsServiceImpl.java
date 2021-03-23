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
import org.worldcubeassociation.statistics.exception.NotFoundException;
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

        for (StatisticsGroupRequestDTO query : statisticsRequestDTO.getQueries()) {
            DatabaseQueryBaseDTO sqlResult = databaseQueryService.getResultSet(query.getSqlQuery());

            StatisticsGroupResponseDTO statisticsGroupResponseDTO = new StatisticsGroupResponseDTO();
            statisticsGroupResponseDTO.setKeys(query.getKeys());
            statisticsGroupResponseDTO.setContent(sqlResult.getContent());
            statisticsResponseDTO.getStatistics().add(statisticsGroupResponseDTO);

            statisticsResponseDTO.setHeaders(
                    // First option is the headers provided in this key
                    Optional.ofNullable(query.getHeaders())
                            // Then, the one provided by the query
                            .orElse(sqlResult.getHeaders()));

            if (statisticsResponseDTO.getHeaders().size() != sqlResult.getHeaders().size()) {
                throw new InvalidParameterException(
                        "The provided headers length and the response headers length must match. If you are "
                                + "unsure, leave it empty.");
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

    @Override
    public List<ControlItemDTO> list() throws IOException {
        File controlFile = getControlFile();

        List<ControlItemDTO> controlList = new ArrayList<>();

        File folder = controlFile.getParentFile();
        List<String> statistics =
                Arrays.stream(folder.list()).filter(name -> !controlFile.getName().endsWith(name)).collect(
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

        return controlList;
    }

    @Override
    public StatisticsResponseDTO getStatistic(String pathId) throws IOException {
        File file = new File(String.format("statistics-list/%s.json", pathId));
        if (!file.exists()) {
            throw new NotFoundException(String.format("Statistic %s does not exists", pathId));
        }
        return MAPPER.readValue(file, StatisticsResponseDTO.class);
    }

    private File getControlFile() {
        return new File("statistics-list/_control-list_.json");
    }

    private void validateRequest(StatisticsRequestDTO statisticsRequestDTO) {
        List<StatisticsGroupRequestDTO> queries = statisticsRequestDTO.getQueries();
        for (StatisticsGroupRequestDTO query : queries) {

            // TODO check if this is needed since there is a @Valid annotation
            if (query == null) {
                throw new InvalidParameterException("Query item must not be null.");
            }

        }

        // Key emptiness validation already happens in the bean, so we can validate just if keys are unique
        if (queries.size() != queries.stream().map(StatisticsGroupRequestDTO::getKeys).distinct().count()) {
            throw new InvalidParameterException("The identifier keys must be unique.");
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
        List<ControlItemDTO> controlList = list();
        File controlFile = getControlFile();
        MAPPER.writeValue(controlFile, controlList);
        log.info("List updated");
    }
}
