package org.worldcubeassociation.statistics.dto.rank;

import lombok.Data;

import java.util.List;

@Data
public class SumOfRanksDto {
    private Integer regionRank;
    private String region;
    private String regionType;
    private String wcaId;
    private Integer overall;
    private List<SumOfRankEventDto> events;
}
