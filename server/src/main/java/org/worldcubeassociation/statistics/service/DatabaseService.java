package org.worldcubeassociation.statistics.service;

import java.util.LinkedHashMap;
import java.util.List;

@FunctionalInterface
public interface DatabaseService {
	public List<LinkedHashMap<String, String>> getResultSet(String sqlQuery);
}
