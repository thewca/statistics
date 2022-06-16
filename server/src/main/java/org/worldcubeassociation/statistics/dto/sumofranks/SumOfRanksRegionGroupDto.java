package org.worldcubeassociation.statistics.dto.sumofranks;

import lombok.Data;

import java.util.List;

@Data
public class SumOfRanksRegionGroupDto {
    private String regionType;
    private List<SumOfRanksRegionDto> regions;
}
