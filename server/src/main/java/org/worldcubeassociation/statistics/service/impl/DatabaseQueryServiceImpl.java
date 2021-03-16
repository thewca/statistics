package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
    public DatabaseQueryDTO getResultSet(String query, Integer page, Integer size) throws InvalidParameterException {
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

        List<LinkedHashMap<String, String>> sqlResult;
        int count; // TODO cache since we are querying db twice

        try {
            sqlResult = jdbcTemplate.query(finalQuery, resultSetRowMapper);
            count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        } catch (Exception e) {
            log.error("" + e);

            // A bit generic, but it's more likely to be user's fault.
            throw new InvalidParameterException(e.getCause().getMessage());
        }

        int totalPages = (int) Math.round(Math.ceil(1.0 * count / size));

        databaseQueryDTO.setNumber(page);
        databaseQueryDTO.setNumberOfElements(sqlResult.size());
        databaseQueryDTO.setSize(size);
        databaseQueryDTO.setTotalElements(count);
        databaseQueryDTO.setTotalPages(totalPages);
        databaseQueryDTO.setHasContent(!sqlResult.isEmpty());
        databaseQueryDTO.setHasNextPage(page < totalPages);
        databaseQueryDTO.setHasPreviousPage(page > 0);
        databaseQueryDTO.setFirstPage(page == 0);
        databaseQueryDTO.setLastPage(page == totalPages);

        if (sqlResult.isEmpty()) {
            databaseQueryDTO.setContent(new ArrayList<>());
            databaseQueryDTO.setHeaders(new ArrayList<>());
            return databaseQueryDTO;
        }

        // Since we are using LinkedHashMap, the headers should be the same accross
        // every line of the content.
        // We take the first one.
        List<String> headers = sqlResult.get(0).entrySet().stream().map(entry -> entry.getKey())
                .collect(Collectors.toList());

        List<List<String>> content = new ArrayList<>();
        sqlResult.forEach(hash -> {
            List<String> list = new ArrayList<>();
            hash.entrySet().forEach(entry -> list.add(entry.getValue()));
            content.add(list);
        });
        databaseQueryDTO.setHeaders(headers);
        databaseQueryDTO.setContent(content);

        return databaseQueryDTO;
    }

}
