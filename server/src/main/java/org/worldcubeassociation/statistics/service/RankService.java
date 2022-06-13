package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;

import java.util.List;

public interface RankService {
    void generateSumOfRanks();

    List<SumOfRanksDto> getSumOfRanks(String regionType, String region, String resultType, int page, int pageSize);
}
