package org.worldcubeassociation.statistics.service.impl;

import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.service.DatabaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatabaseServiceImpl implements DatabaseService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<LinkedHashMap<String, String>> getResultSet(String query) {
		log.info("Execute query\n{}", query);

		return jdbcTemplate.query(query, (rs, rowNum) -> {
			// Makes the result set into 1 result
			LinkedHashMap<String, String> out = new LinkedHashMap<>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				out.put(name, rs.getString(i));
			}
			return out;
		});
	}

}
