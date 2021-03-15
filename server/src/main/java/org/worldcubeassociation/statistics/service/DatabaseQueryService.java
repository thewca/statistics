package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;

@FunctionalInterface
public interface DatabaseQueryService {
    DatabaseQueryDTO getResultSet(String query, Integer page, Integer size) throws InvalidParameterException;
}
