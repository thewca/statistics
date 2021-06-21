package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.DatabaseQueryBaseDTO;
import org.worldcubeassociation.statistics.dto.DatabaseQueryDTO;
import org.worldcubeassociation.statistics.request.DatabaseQueryRequest;

public interface DatabaseQueryService {
    DatabaseQueryDTO getResultSet(DatabaseQueryRequest databaseQueryRequest, String accessToken);

    DatabaseQueryBaseDTO getResultSet(String query);
}
