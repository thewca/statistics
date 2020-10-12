package org.worldcubeassociation.statistics.rowmapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * General result set rowmapper. It takes any sql result and converts to a
 * LinkedHashMap composed with <columnName, value>
 * 
 */
@Component
public class ResultSetRowMapper implements RowMapper<LinkedHashMap<String, String>> {

	/**
	 * Makes the result set into 1 result
	 */
	@Override
	public LinkedHashMap<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();

		// The column count starts from 1
		for (int i = 1; i <= columnCount; i++) {
			String name = rsmd.getColumnName(i);
			result.put(name, rs.getString(i));
		}
		return result;
	}

}
