package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;

import java.util.List;
import java.util.Optional;

public interface SumOfRanksRepository {
    int generateWorldRank();

    int deleteAll();

    int generateContinentRank();

    int generateCountryRank();

    List<SumOfRanksDto> list(String resultType, String regionType, String region, int page, int pageSize);

    List<SumOfRanksMetaDto> getResultTypes();

    int deleteAllMeta();

    int insertMeta();

    void updateRanks();

    Optional<Integer> getWcaIdPage(String resultType, String regionType, String region, int pageSize, String wcaId);
}
