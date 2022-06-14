package org.worldcubeassociation.statistics.dto.sumofranks;

import lombok.Data;
import org.worldcubeassociation.statistics.dto.EventDto;

import java.util.List;

@Data
public class SumOfRanksMetaDto {
    private List<SumOfRanksResultTypeDto> resultTypes;
    private List<EventDto> availableEvents;
}
