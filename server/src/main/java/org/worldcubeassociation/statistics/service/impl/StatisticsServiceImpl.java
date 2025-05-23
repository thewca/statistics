package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.Statistics;
import org.worldcubeassociation.statistics.repository.StatisticsRepository;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;
import org.worldcubeassociation.statistics.service.StatisticsControlService;
import org.worldcubeassociation.statistics.service.StatisticsService;
import org.yaml.snakeyaml.Yaml;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final DatabaseQueryService databaseQueryService;
    private final ResourceLoader resourceLoader;
    private final StatisticsRepository statisticsRepository;
    private final ObjectMapper objectMapper;
    private final StatisticsControlService statisticsControlService;

    private static final Yaml YAML = new Yaml();

    // Website loading is taking 1min. This is a temporary solution to cache the statistics
    // We should move to redis to cache the statistics instead
    private static final Map<String, Pair<LocalDateTime, Object>> CACHE = new HashMap<>();
    private static final String MAIN_LIST_CACHE = "MAIN_LIST_CACHE";
    private static final int CACHING_TIME = 6;

    public StatisticsServiceImpl(DatabaseQueryService databaseQueryService,
        ResourceLoader resourceLoader, StatisticsRepository statisticsRepository,
        ObjectMapper objectMapper, StatisticsControlService statisticsControlService) {
        this.databaseQueryService = databaseQueryService;
        this.resourceLoader = resourceLoader;
        this.statisticsRepository = statisticsRepository;
        this.objectMapper = objectMapper;
        this.statisticsControlService = statisticsControlService;
    }

    @Override
    public StatisticsResponseDTO sqlToStatistics(StatisticsRequestDTO statisticsRequestDTO) {
        log.info("SQL to statistics for {}", statisticsRequestDTO);

        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setStatistics(new ArrayList<>());

        for (StatisticsGroupRequestDTO query : statisticsRequestDTO.getQueries()) {
            DatabaseQueryBaseDTO sqlResult = databaseQueryService.getResultSet(query.getSqlQuery());

            buildStatistics(statisticsDTO, query, sqlResult);
        }

        statisticsDTO
            .setDisplayMode(
                Optional.ofNullable(statisticsRequestDTO.getDisplayMode())
                    .orElse(DisplayModeEnum.DEFAULT));
        statisticsDTO.setExplanation(statisticsRequestDTO.getExplanation());
        statisticsDTO.setTitle(statisticsRequestDTO.getTitle());
        statisticsDTO.setGroupName(statisticsRequestDTO.getGroupName());

        return create(statisticsDTO);
    }

    private void buildStatistics(StatisticsDTO statisticsDTO, StatisticsGroupRequestDTO query,
        DatabaseQueryBaseDTO sqlResult) {
        if (query.getKeyColumnIndex() == null) {
            addResult(query, statisticsDTO, query.getKeys(), sqlResult.getContent(),
                sqlResult.getHeaders());
        } else {
            var map = new LinkedHashMap<String, List<List<String>>>();

            for (var result : sqlResult.getContent()) {
                if (!map.containsKey(result.get(query.getKeyColumnIndex()))) {
                    map.put(result.get(query.getKeyColumnIndex()), new ArrayList<>());
                }
                List<String> list = new ArrayList<>();
                for (var i = 0; i < result.size(); i++) {
                    if (i != query.getKeyColumnIndex()) {
                        list.add(result.get(i));
                    }
                }
                map.get(result.get(query.getKeyColumnIndex())).add(list);
            }

            for (var entries : map.entrySet()) {
                addResult(query, statisticsDTO, List.of(entries.getKey().split(",")),
                    entries.getValue(),
                    sqlResult.getHeaders());
            }
        }
    }

    private void addResult(StatisticsGroupRequestDTO query, StatisticsDTO statisticsDTO,
        List<String> key,
        List<List<String>> content, List<String> headers) {
        StatisticsGroupResponseDTO statisticsGroupResponseDTO = new StatisticsGroupResponseDTO();
        statisticsGroupResponseDTO.setKeys(key);
        statisticsGroupResponseDTO.setContent(content);
        statisticsGroupResponseDTO.setShowPositions(query.getShowPositions());
        statisticsGroupResponseDTO.setPositionTieBreakerIndex(query.getPositionTieBreakerIndex());
        statisticsGroupResponseDTO.setExplanation(query.getExplanation());
        statisticsGroupResponseDTO.setSqlQueryCustom(query.getSqlQueryCustom());
        statisticsGroupResponseDTO.setHeaders(
            // First option is the headers provided in this key
            Optional.ofNullable(query.getHeaders())
                // Then, the one provided by the query
                .orElse(headers));

        statisticsDTO.getStatistics().add(statisticsGroupResponseDTO);
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
            if (!resource.exists()) {
                throw new NotFoundException("File " + resource.getFilename() + " does not exist");
            }

            log.info("Statistic {}", resource.getDescription());

            var statisticsControl = statisticsControlService.start(resource.getFilename());

            try {
                InputStream inputStream = resource.getInputStream();

                StatisticsRequestDTO request = YAML.loadAs(inputStream, StatisticsRequestDTO.class);

                sqlToStatistics(request);

                statisticsControlService.complete(statisticsControl);
            } catch (Exception e) {
                log.error("Error while processing {}", resource.getFilename(), e);
                statisticsControlService.error(statisticsControl, e.getMessage());
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticsListDTO list(String term) {
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

        if (StringUtils.isBlank(term)) {
            var cache = CACHE.get(MAIN_LIST_CACHE);
            if (cache != null && cache.getFirst().plusHours(CACHING_TIME)
                .isAfter(LocalDateTime.now())) {
                return (StatisticsListDTO) cache.getSecond();
            }
        }

        List<ControlItemDTO> controlList = statisticsRepository.list(term);

        List<StatisticsGroupDTO> list =
            controlList.stream()
                .collect(Collectors.groupingBy(ControlItemDTO::getGroupName, Collectors.toList()))
                .entrySet()
                .stream()
                .map(k -> StatisticsGroupDTO.builder().group(k.getKey())
                    .statistics(k.getValue()
                        // Sort inner statistics based on title
                        .stream().sorted(Comparator.comparing(ControlItemDTO::getTitle))
                        .toList()).build())
                // Sorts groups based on group name
                .sorted(Comparator.comparing(StatisticsGroupDTO::getGroup))
                .toList();

        StatisticsListDTO statisticsListDTO = new StatisticsListDTO();
        statisticsListDTO.setList(list);

        if (StringUtils.isBlank(term)) {
            CACHE.put(MAIN_LIST_CACHE, Pair.of(LocalDateTime.now(), statisticsListDTO));
        }

        return statisticsListDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticsResponseDTO getStatistic(String path) {
        var cache = CACHE.get(path);
        if (cache != null && cache.getFirst().plusHours(CACHING_TIME)
            .isAfter(LocalDateTime.now())) {
            return (StatisticsResponseDTO) cache.getSecond();
        }

        Statistics statistics = statisticsRepository.findById(path)
            .orElseThrow(
                () -> new NotFoundException(String.format("Statistic %s does not exists", path)));
        var toReturn = objectMapper.convertValue(statistics, StatisticsResponseDTO.class);
        CACHE.put(path, Pair.of(LocalDateTime.now(), toReturn));
        return toReturn;
    }

    @Override
    public StatisticsResponseDTO create(@Valid StatisticsDTO statisticsDTO) {
        log.info("Create statistics from {}", statisticsDTO);

        statisticsDTO
            .setDisplayMode(Optional.ofNullable(statisticsDTO.getDisplayMode())
                .orElse(DisplayModeEnum.DEFAULT));

        StatisticsResponseDTO statisticsResponseDTO =
            objectMapper.convertValue(statisticsDTO, StatisticsResponseDTO.class);

        String path = String.join("-",
                StringUtils.stripAccents(statisticsDTO.getTitle().replaceAll("[^a-zA-Z0-9 ]", ""))
                    .split(" "))
            .toLowerCase();

        statisticsResponseDTO.setPath(path);
        statisticsResponseDTO.setGroupName(statisticsDTO.getGroupName());

        statisticsResponseDTO.getStatistics().forEach(
            stat -> Optional.ofNullable(stat.getSqlQueryCustom()).ifPresent(
                q -> stat.setSqlQueryCustom(URLEncoder.encode(q, StandardCharsets.UTF_8))));

        saveStatistics(statisticsResponseDTO);

        return statisticsResponseDTO;
    }

    @Override
    public void deleteAll() {
        log.info("Delete all statistics");
        statisticsRepository.deleteAll();
        statisticsControlService.deleteAll();
        log.info("Deleted");
    }

    @Override
    public LocalDateTime getExportDate() {
        return statisticsRepository.getExportDate();
    }

    private Statistics saveStatistics(StatisticsResponseDTO statisticsResponseDTO) {
        Statistics statistics = objectMapper.convertValue(statisticsResponseDTO, Statistics.class);
        statistics.setLastModified(LocalDateTime.now());
        statistics.setExportDate(getExportDate());
        return statisticsRepository.save(statistics);
    }
}
