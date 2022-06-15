package org.worldcubeassociation.statistics.dto.sumofranks;

import lombok.Data;

import java.util.List;

@Data
public class SumOfRanksDto {
    private Integer regionRank;
    private String wcaId;
    private String name;
    private String countryIso2;
    private Integer overall;
    private List<SumOfRankEventDto> events;
}
