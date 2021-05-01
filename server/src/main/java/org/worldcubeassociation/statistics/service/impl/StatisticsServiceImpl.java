package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsGroupResponseDTO;
import org.worldcubeassociation.statistics.dto.StatisticsListDTO;
import org.worldcubeassociation.statistics.dto.StatisticsRequestDTO;
import org.worldcubeassociation.statistics.dto.StatisticsResponseDTO;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.Statistics;
import org.worldcubeassociation.statistics.repository.StatisticsRepository;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;
import org.worldcubeassociation.statistics.service.StatisticsService;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private DatabaseQueryService databaseQueryService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private StatisticsRepository statisticsRepository;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Yaml YAML = new Yaml();

    static {
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) {
        log.info("SQL to statistics for {}", statisticsRequestDTO);

        validateRequest(statisticsRequestDTO);

        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setStatistics(new ArrayList<>());

        for (StatisticsGroupRequestDTO query : statisticsRequestDTO.getQueries()) {
            DatabaseQueryBaseDTO sqlResult = databaseQueryService.getResultSet(query.getSqlQuery());

            StatisticsGroupResponseDTO statisticsGroupResponseDTO = new StatisticsGroupResponseDTO();
            statisticsGroupResponseDTO.setKeys(query.getKeys());
            statisticsGroupResponseDTO.setContent(sqlResult.getContent());
            statisticsGroupResponseDTO.setShowPositions(query.getShowPositions());
            statisticsGroupResponseDTO.setPositionTieBreakerIndex(query.getPositionTieBreakerIndex());
            statisticsGroupResponseDTO.setExplanation(query.getExplanation());
            statisticsGroupResponseDTO.setSqlQueryCustom(query.getSqlQueryCustom());
            statisticsDTO.getStatistics().add(statisticsGroupResponseDTO);

            statisticsGroupResponseDTO.setHeaders(
                    // First option is the headers provided in this key
                    Optional.ofNullable(query.getHeaders())
                            // Then, the one provided by the query
                            .orElse(sqlResult.getHeaders()));

            if (statisticsGroupResponseDTO.getHeaders().size() != sqlResult.getHeaders().size()) {
                throw new InvalidParameterException(
                        "The provided headers length and the response headers length must match. If you are "
                                + "unsure, leave it empty.");
            }
        }

        statisticsDTO
                .setDisplayMode(
                        Optional.ofNullable(statisticsRequestDTO.getDisplayMode()).orElse(DisplayModeEnum.DEFAULT));
        statisticsDTO.setExplanation(statisticsRequestDTO.getExplanation());
        statisticsDTO.setTitle(statisticsRequestDTO.getTitle());
        statisticsDTO.setGroup(statisticsRequestDTO.getGroup());

        return create(statisticsDTO);
    }

    @Override
    public void generateAllFromSql() throws IOException {
        log.info("Find all statistics");

        List<Resource> resources =
                Arrays.asList(ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                        .getResources("classpath:statistics-request-list/*.yml"));

        resourcesToStatistics(resources);
        log.info("Generated");
    }

    @Override
    public void generateFromSql(String filename) throws IOException {
        log.info("Generate statistics from the file {}", filename);

        List<Resource> resources =
                Arrays.asList(ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                        .getResources(String.format("classpath:statistics-request-list/%s.yml", filename)));

        if (resources.isEmpty()) {
            throw new NotFoundException(String.format("Resource %s not found", filename));
        }

        resourcesToStatistics(resources);
    }

    private void resourcesToStatistics(List<Resource> resources) throws IOException {
        for (Resource resource : resources) {
            log.info("Statistic {}", resource.getDescription());

            InputStream inputStream = resource.getInputStream();

            StatisticsRequestDTO request = YAML.loadAs(inputStream, StatisticsRequestDTO.class);

            sqlToStatistics(request);
        }
    }

    @Override
    public StatisticsListDTO list() {
        /* It would be better if we could write a query to retrieve the items ordered already, but mysql does not
        support it
        https://dev.mysql.com/doc/refman/8.0/en/aggregate-functions.html#function_json-arrayagg
        Something like

        select
            group_name,
            json_arrayagg(json_object('title', title, 'path', path)) statistics -- This part is not ordered
        from
            statistics
        group by
            group_name
        order by
            group_name */

        List<ControlItemDTO> controlList = statisticsRepository.list();

        List<StatisticsGroupDTO> list =
                controlList.stream().collect(Collectors.groupingBy(it -> it.getGroupName(), Collectors.toList()))
                        .entrySet()
                        .stream()
                        .map((k) -> StatisticsGroupDTO.builder().group(k.getKey())
                                .statistics(k.getValue()
                                        // Sort inner statistics based on title
                                        .stream().sorted(Comparator.comparing(ControlItemDTO::getTitle))
                                        .collect(Collectors.toList())).build())
                        // Sorts groups based on group name
                        .sorted(Comparator.comparing(StatisticsGroupDTO::getGroup))
                        .collect(Collectors.toList());

        StatisticsListDTO statisticsListDTO = new StatisticsListDTO();
        statisticsListDTO.setList(list);

        return statisticsListDTO;
    }

    @Override
    public StatisticsDTO getStatistic(String path) {
        return statisticsRepository.findById(path)
                .orElseThrow(() -> new NotFoundException(String.format("Statistic %s does not exists", path)))
                .convert();
    }

    @Override
    public StatisticsResponseDTO create(@Valid StatisticsDTO statisticsDTO) {
        log.info("Create statistics from {}", statisticsDTO);

        statisticsDTO
                .setDisplayMode(Optional.ofNullable(statisticsDTO.getDisplayMode()).orElse(DisplayModeEnum.DEFAULT));

        StatisticsResponseDTO statisticsResponseDTO = MAPPER.convertValue(statisticsDTO, StatisticsResponseDTO.class);

        String path = String.join("-",
                StringUtils.stripAccents(statisticsDTO.getTitle().replaceAll("[^a-zA-Z0-9 ]", "")).split(" "))
                .toLowerCase();

        statisticsResponseDTO.setPath(path);
        statisticsResponseDTO.setGroup(statisticsDTO.getGroup());

        statisticsDTO.getStatistics().forEach(stat -> {
            Optional.ofNullable(stat.getSqlQueryCustom()).ifPresent(q -> stat.setSqlQueryCustom(
                    URLEncoder.encode(q, StandardCharsets.UTF_8)));
        });

        saveStatistics(statisticsResponseDTO).convert();

        return statisticsResponseDTO;
    }

    @Override
    public void deleteAll() {
        log.info("Delete all statistics");
        statisticsRepository.deleteAll();
        log.info("Deleted");
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

    private Statistics saveStatistics(StatisticsResponseDTO statisticsResponseDTO) {
        Statistics statistics = new Statistics();
        statistics.setStatistics(statisticsResponseDTO.getStatistics());
        statistics.setExplanation(statisticsResponseDTO.getExplanation());
        statistics.setGroupName(statisticsResponseDTO.getGroup());
        statistics.setPath(statisticsResponseDTO.getPath());
        statistics.setTitle(statisticsResponseDTO.getTitle());
        statistics.setDisplayMode(statisticsResponseDTO.getDisplayMode());

        return statisticsRepository.save(statistics);
    }
}
