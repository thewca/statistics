package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksRegionGroupDto;

import java.util.List;

public interface SumOfRanksRepository {
    void generateWorldRank();

    void deleteAll();

    void generateContinentRank();

    void generateCountryRank();

    List<SumOfRanksDto> list(String regionType, String region, String resultType, int page, int pageSize);

    List<SumOfRanksRegionGroupDto> getRegions();
}
