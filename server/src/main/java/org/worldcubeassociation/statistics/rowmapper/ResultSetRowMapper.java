package org.worldcubeassociation.statistics.rowmapper;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * General result set rowmapper. It takes any sql result and converts to a
 * LinkedHashMap composed with <columnName, value>
 */
@Component
public class ResultSetRowMapper implements RowMapper<Pair<List<String>, List<String>>> {

    /**
     * Makes the result set into 1 result
     */
    @Override
    public Pair<List<String>, List<String>> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<String> headers = new ArrayList<>();
        List<String> row = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        // The column count starts from 1
        for (int i = 1; i <= columnCount; i++) {
            String name = rsmd.getColumnName(i);
            headers.add(name);
            row.add(rs.getString(i));
        }
        return Pair.of(headers, row);
    }

}
