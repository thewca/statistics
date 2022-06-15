package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksResultTypeDto;

import java.util.List;

public interface SumOfRanksRepository {
    void generateWorldRank();

    int deleteAll();

    void generateContinentRank();

    void generateCountryRank();

    List<SumOfRanksDto> list(String resultType, String regionType, String region, int page, int pageSize);

    List<SumOfRanksResultTypeDto> getResultTypes();

    int deleteAllMeta();

    int insertMeta();
}
