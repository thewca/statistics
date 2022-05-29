package org.worldcubeassociation.statistics.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;
import org.worldcubeassociation.statistics.response.DatabaseQueryMetaResponse;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;
import org.worldcubeassociation.statistics.service.DatabaseQueryService;
import org.worldcubeassociation.statistics.service.StatisticsService;
import org.worldcubeassociation.statistics.util.LoadResourceUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DatabaseQueryServiceImpl implements DatabaseQueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResultSetRowMapper resultSetRowMapper;

    @Autowired
    private StatisticsService statisticsService;

    @Value("${service.seconds-to-timeout}")
    private int secondsToTimeout;

    private static List<CachedToken> cachedTokens = new ArrayList<>();

    private static final String PAGINATION_WRAPPER = "select * from (\n%s\n) alias limit %s offset %s";
    private static final String PAGINATION_COUNT = "select count(*) from (\n%s\n) alias";

    @Override
    public DatabaseQueryDTO getResultSet(DatabaseQueryRequest databaseQueryRequest, String accessToken) {
        validateToken(accessToken);
        DatabaseQueryDTO result = null;
        try {
            result = getResultSet(databaseQueryRequest);
        } catch (DataAccessException | SQLException e) {
            log.info("{}", e.toString());
            throw new InvalidParameterException(e.getMessage());
        } finally {
            removeCachedToken(accessToken);
        }

        return result;
    }

    public DatabaseQueryDTO getResultSet(DatabaseQueryRequest databaseQueryRequest) throws SQLException {
        String query = databaseQueryRequest.getSqlQuery();
        int page = databaseQueryRequest.getPage();
        int size = databaseQueryRequest.getSize();

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

        // TODO cache since we are querying db twice in case of next page
        int count = jdbcTemplate.queryForObject(countQuery, Integer.class);

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

    private void validateToken(String accessToken) {
        CachedToken newToken = new CachedToken();
        newToken.setToken(accessToken);
        int index = Collections.binarySearch(cachedTokens, newToken);

        if (index < 0) {
            // The user was not doing any requisition
            newToken.setCacheTime(LocalDateTime.now());
            cachedTokens.add(-index - 1, newToken);
            return;
        }

        CachedToken cachedToken = cachedTokens.get(index);
        LocalDateTime now = LocalDateTime.now();
        long diff = cachedToken.getCacheTime().until(now, ChronoUnit.SECONDS);
        if (diff > secondsToTimeout) {
            // In this scenario, the controller probably killed a connection. The cache is idle because it was not
            // removed after getting the result set

            cachedToken.setCacheTime(now);
            return;
        }

        // We block, in the server, multiple request from the same user
        throw new InvalidParameterException(
                "You can't get multiple queries running in parallel. Wait for it to finish or until "
                        + cachedToken.getCacheTime().plusSeconds(secondsToTimeout));
    }

    private void removeCachedToken(String accessToken) {
        // We remove token cached token so the user can query again
        CachedToken cachedToken = new CachedToken();
        cachedToken.setToken(accessToken);
        int index = Collections.binarySearch(cachedTokens, cachedToken);
        cachedTokens.remove(index);
    }

    public DatabaseQueryBaseDTO getResultSet(String query) {
        log.info("Get result set for {}", query);

        DatabaseQueryBaseDTO result = new DatabaseQueryBaseDTO();

        List<Pair<List<String>, List<String>>> sqlResult;
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

            // The headers should be the same across every line of the content. We take the first one.
            List<String> headers = sqlResult.get(0).getFirst();
            List<List<String>> content = sqlResult.stream().map(Pair::getSecond).collect(Collectors.toList());
            result.setHeaders(headers);
            result.setContent(content);
        }

        return result;
    }

    @Override
    public DatabaseQueryMetaResponse meta() {
        return DatabaseQueryMetaResponse.builder().exportDate(statisticsService.getExportDate().toLocalDate())
                .additionalInformation(
                        LoadResourceUtil.getResource("databasequery/additionalInformation.html")).build();
    }

    @Data
    private static class CachedToken implements Comparable<CachedToken> {
        private String token;
        private LocalDateTime cacheTime;

        @Override
        public int compareTo(CachedToken other) {
            return token.compareTo(other.token);
        }
    }
}
