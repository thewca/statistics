package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.vo.DatabaseQueryVo;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;

@FunctionalInterface
public interface DatabaseQueryService {
    DatabaseQueryVo getResultSet(String query, Integer page, Integer size) throws InvalidParameterException;
}
