/**
 *
 */
package org.worldcubeassociation.statistics.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseQueryServiceImplTest {

    @InjectMocks
    private DatabaseQueryServiceImpl service;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ResultSetRowMapper resultSetRowMapper;

    private String query = "select * from Events";

    @Test
    public void getResultSetTest() {
        // Ideal scenario

        int nEvents = 20;

        // We simulate a result set. This is a simulation of Events
        List<String> tableHeaders = Arrays.asList("id", "name", "rank", "format", "cellName");

        List<Pair<List<String>, List<String>>> sqlResult = new ArrayList<>();

        for (int i = 0; i < nEvents; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < tableHeaders.size(); j++) {
                row.add(" puzzle " + j + tableHeaders.get(j));
            }
            sqlResult.add(Pair.of(tableHeaders, row));
        }

        when(jdbcTemplate.query(anyString(), any(ResultSetRowMapper.class))).thenReturn(sqlResult);
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class))).thenReturn(100);

        DatabaseQueryRequest databaseQueryRequest = new DatabaseQueryRequest();
        databaseQueryRequest.setSqlQuery(query);
        databaseQueryRequest.setPage(0);
        databaseQueryRequest.setSize(25);

        String accessToken = "access token";
        DatabaseQueryDTO result = service.getResultSet(databaseQueryRequest, accessToken);

        assertEquals(tableHeaders.size(), result.getHeaders().size());
        assertEquals(nEvents, result.getContent().size());
    }

    @Test
    public void getResultSetEmptyTest() {
        // In case of no result, we should return an empty list

        when(jdbcTemplate.queryForObject(anyString(), any(Class.class))).thenReturn(100);

        DatabaseQueryRequest databaseQueryRequest = new DatabaseQueryRequest();
        databaseQueryRequest.setSqlQuery(query);
        databaseQueryRequest.setPage(0);
        databaseQueryRequest.setSize(25);

        String accessToken = "access token";
        DatabaseQueryDTO result = service.getResultSet(databaseQueryRequest, accessToken);

        assertTrue(result.getHeaders().isEmpty());
        assertTrue(result.getContent().isEmpty());
    }
}
