package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;
import org.worldcubeassociation.statistics.service.DatabaseService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResultSetRowMapper resultSetRowMapper;

    private static final String PAGINATION_WRAPPER = "select * from (%s) alias limit %s offset %s";
    private static final String PAGINATION_COUNT = "select count(*) from (%s) alias";

    @Override
    public ResultSetResponse getResultSet(String query, Integer page, Integer size) throws InvalidParameterException {
        log.info("Execute query\n{}", query);

        int pageNumber = Optional.ofNullable(page).orElse(0);
        int limit = Optional.ofNullable(size).orElse(25);

        query = query.trim();
        int index = query.length() - 1;
        while (index >= 0 && (query.charAt(index) == ';' || Character.isWhitespace(query.charAt(index)))) {
            index--;
        }

        query = query.substring(0, index + 1);

        String countQuery = String.format(PAGINATION_COUNT, query);
        String finalQuery = String.format(PAGINATION_WRAPPER, query, limit, limit * pageNumber);
        log.info("Final query\n{}", finalQuery);

        ResultSetResponse resultSetResponse = new ResultSetResponse();

        List<LinkedHashMap<String, String>> sqlResult;
        int count; // TODO cache

        try {
            sqlResult = jdbcTemplate.query(finalQuery, resultSetRowMapper);
            count = jdbcTemplate.queryForObject(countQuery, Integer.class);
        } catch (Exception e) {
            log.error("" + e);

            // A bit generic, but it's more likely to be user's fault.
            throw new InvalidParameterException(e.getCause().getMessage());
        }

        int totalPages = (int) Math.round(Math.ceil(1.0 * count / limit));

        resultSetResponse.setNumber(pageNumber);
        resultSetResponse.setNumberOfElements(sqlResult.size());
        resultSetResponse.setSize(limit);
        resultSetResponse.setTotalElements(count);
        resultSetResponse.setTotalPages(totalPages);
        resultSetResponse.setHasContent(!sqlResult.isEmpty());
        resultSetResponse.setHasNextPage(pageNumber < totalPages);
        resultSetResponse.setHasPreviousPage(pageNumber > 0);
        resultSetResponse.setFirstPage(pageNumber == 0);
        resultSetResponse.setLastPage(pageNumber == totalPages);

        if (sqlResult.isEmpty()) {
            resultSetResponse.setContent(new ArrayList<>());
            resultSetResponse.setHeaders(new ArrayList<>());
            return resultSetResponse;
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
        resultSetResponse.setHeaders(headers);
        resultSetResponse.setContent(content);

        return resultSetResponse;
    }

}
