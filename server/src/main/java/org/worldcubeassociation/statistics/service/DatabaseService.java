package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.controller.response.ResultSetResponse;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;

@FunctionalInterface
public interface DatabaseService {
    ResultSetResponse getResultSet(String query, Integer page, Integer size) throws InvalidParameterException;
}
