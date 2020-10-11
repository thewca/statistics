package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;

@FunctionalInterface
public interface DatabaseService {
	public ResultSetResponse getResultSet(String sqlQuery);
}
