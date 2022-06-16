package org.worldcubeassociation.statistics.dto.sumofranks;

import lombok.Data;
import org.worldcubeassociation.statistics.dto.EventDto;

@Data
public class SumOfRankEventDto {
    private EventDto event;
    private Integer regionalRank;
    private Boolean completed;
}
