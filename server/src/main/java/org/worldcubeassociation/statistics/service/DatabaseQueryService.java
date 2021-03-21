package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;

public interface DatabaseQueryService {
    DatabaseQueryDTO getResultSet(String query, Integer page, Integer size);

    DatabaseQueryBaseDTO getResultSet(String query);
}
