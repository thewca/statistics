package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;

import java.util.List;

public interface SumOfRanksService {
    void generate();

    List<SumOfRanksDto> list(String regionType, String region, String resultType, int page, int pageSize);
}
