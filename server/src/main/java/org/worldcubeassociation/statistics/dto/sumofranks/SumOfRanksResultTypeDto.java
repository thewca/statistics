package org.worldcubeassociation.statistics.dto.sumofranks;

import lombok.Data;

import java.util.List;

@Data
public class SumOfRanksResultTypeDto {
    private String resultType;
    private List<SumOfRanksRegionGroupDto> regionGroups;
}
