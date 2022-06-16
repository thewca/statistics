package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
import org.worldcubeassociation.statistics.response.PageResponse;

import java.util.List;

public interface SumOfRanksService {
    void generate();

    PageResponse<SumOfRanksDto> list(String resultType, String regionType, String region, int page, int pageSize,
                                     String wcId);

    List<SumOfRanksMetaDto> meta();
}
