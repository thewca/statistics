/**
 *
 */
package org.worldcubeassociation.statistics.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        String[] tableHeaders = new String[]{"id", "name", "rank", "format", "cellName"};

        List<LinkedHashMap<String, String>> sqlResult = new ArrayList<>();

        for (int i = 0; i < nEvents; i++) {
            LinkedHashMap<String, String> puzzleResult = new LinkedHashMap<>();
            for (int j = 0; j < tableHeaders.length; j++) {
                puzzleResult.put(tableHeaders[j], " puzzle " + j + tableHeaders[j]);
            }
            sqlResult.add(puzzleResult);
        }

        when(jdbcTemplate.query(anyString(), any(ResultSetRowMapper.class))).thenReturn(sqlResult);
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class))).thenReturn(100);

        DatabaseQueryRequest databaseQueryRequest = new DatabaseQueryRequest();
        databaseQueryRequest.setSqlQuery(query);
        databaseQueryRequest.setPage(0);
        databaseQueryRequest.setSize(25);

        DatabaseQueryDTO result = service.getResultSet(databaseQueryRequest);

        assertEquals(tableHeaders.length, result.getHeaders().size());
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

        DatabaseQueryDTO result = service.getResultSet(databaseQueryRequest);

        assertTrue(result.getHeaders().isEmpty());
        assertTrue(result.getContent().isEmpty());
    }
}
