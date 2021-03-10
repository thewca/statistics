package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;

@FunctionalInterface
public interface DatabaseService {
	public ResultSetResponse getResultSet(String sqlQuery) throws InvalidParameterException;
}
