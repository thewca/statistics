package org.worldcubeassociation.statistics.dto.sumofranks;

import lombok.Data;
import org.worldcubeassociation.statistics.dto.EventDto;

import java.util.List;

@Data
public class SumOfRanksMetaDto {
    private List<SumOfRanksRegionGroupDto> regions;
    private List<EventDto> availableEvents;
}
