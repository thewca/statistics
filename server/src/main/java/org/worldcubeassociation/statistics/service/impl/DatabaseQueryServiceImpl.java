package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DatabaseQueryServiceImpl implements DatabaseQueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResultSetRowMapper resultSetRowMapper;

    private static final String PAGINATION_WRAPPER = "select * from (\n%s\n) alias limit %s offset %s";
    private static final String PAGINATION_COUNT = "select count(*) from (\n%s\n) alias";

    @Override
    public DatabaseQueryDTO getResultSet(String query, Integer page, Integer size) {
        log.info("Execute query\n{}", query);

        query = query.trim();
        int index = query.length() - 1;
        while (index >= 0 && (query.charAt(index) == ';' || Character.isWhitespace(query.charAt(index)))) {
            index--;
        }

        query = query.substring(0, index + 1);

        String countQuery = String.format(PAGINATION_COUNT, query);
        String finalQuery = String.format(PAGINATION_WRAPPER, query, size, size * page);
        log.info("Final query\n{}", finalQuery);

        DatabaseQueryDTO databaseQueryDTO = new DatabaseQueryDTO();

        int count; // TODO cache since we are querying db twice in case of next page
        try {
            count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        } catch (Exception e) {
            log.error("" + e);

            // A bit generic, but it's more likely to be user's fault.
            throw new InvalidParameterException(e.getCause().getMessage());
        }

        DatabaseQueryBaseDTO sqlResult = getResultSet(finalQuery);

        int totalPages = (int) Math.round(Math.ceil(1.0 * count / size));

        databaseQueryDTO.setNumber(page);
        databaseQueryDTO.setNumberOfElements(sqlResult.getContent().size());
        databaseQueryDTO.setSize(size);
        databaseQueryDTO.setTotalElements(count);
        databaseQueryDTO.setTotalPages(totalPages);
        databaseQueryDTO.setHasContent(!sqlResult.getContent().isEmpty());
        databaseQueryDTO.setHasNextPage(page < totalPages);
        databaseQueryDTO.setHasPreviousPage(page > 0);
        databaseQueryDTO.setFirstPage(page == 0);
        databaseQueryDTO.setLastPage(page == totalPages);

        databaseQueryDTO.setHeaders(sqlResult.getHeaders());
        databaseQueryDTO.setContent(sqlResult.getContent());

        return databaseQueryDTO;
    }

    public DatabaseQueryBaseDTO getResultSet(String query) {
        log.info("Get result set for {}", query);

        DatabaseQueryBaseDTO result = new DatabaseQueryBaseDTO();

        List<LinkedHashMap<String, String>> sqlResult;
        try {
            sqlResult = jdbcTemplate.query(query, resultSetRowMapper);
        } catch (Exception e) {
            log.error("" + e);

            // A bit generic, but it's more likely to be user's fault.
            throw new InvalidParameterException(e.getCause().getMessage());
        }

        if (sqlResult.isEmpty()) {
            result.setContent(new ArrayList<>());
            result.setHeaders(new ArrayList<>());
        } else {

            // Since we are using LinkedHashMap, the headers should be the same accross
            // every line of the content.
            // We take the first one.
            List<String> headers =
                    sqlResult.get(0).entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());

            List<List<String>> content = new ArrayList<>();
            sqlResult.forEach(hash -> {
                List<String> list = new ArrayList<>();
                hash.entrySet().forEach(entry -> list.add(entry.getValue()));
                content.add(list);
            });
            result.setHeaders(headers);
            result.setContent(content);
        }

        return result;
    }
}
