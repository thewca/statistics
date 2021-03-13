/**
 * 
 */
package org.worldcubeassociation.statistics.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.rowmapper.ResultSetRowMapper;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseServiceImplTest {

	@InjectMocks
	private DatabaseServiceImpl service;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Mock
	private ResultSetRowMapper resultSetRowMapper;

	private String query = "select * from Events";

	@Test
	public void getResultSetTest() throws InvalidParameterException {
		// Ideal scenario

		int nEvents = 20;

		// We simulate a result set. This is a simulation of Events
		String[] tableHeaders = new String[] { "id", "name", "rank", "format", "cellName" };

		List<LinkedHashMap<String, String>> sqlResult = new ArrayList<>();

		for (int i = 0; i < nEvents; i++) {
			LinkedHashMap<String, String> puzzleResult = new LinkedHashMap<>();
			for (int j = 0; j < tableHeaders.length; j++) {
				puzzleResult.put(tableHeaders[j], " puzzle " + j + tableHeaders[j]);
			}
			sqlResult.add(puzzleResult);
		}

		when(jdbcTemplate.query(anyString(), any(ResultSetRowMapper.class))).thenReturn(sqlResult);

		ResultSetResponse result = service.getResultSet(query, 0, 0);

		assertEquals(tableHeaders.length, result.getHeaders().size());
		assertEquals(nEvents, result.getContent().size());
	}

	@Test
	public void getResultSetEmptyTest() throws InvalidParameterException {
		// In case of no result, we should return an empty list

		ResultSetResponse result = service.getResultSet(query, 0, 0);

		assertTrue(result.getHeaders().isEmpty());
		assertTrue(result.getContent().isEmpty());
	}
}
