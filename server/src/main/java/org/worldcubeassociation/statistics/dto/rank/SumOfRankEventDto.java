package org.worldcubeassociation.statistics.dto.rank;

import lombok.Data;

@Data
public class SumOfRankEventDto {
    private Integer rank;
    private String eventId;
    private Boolean completed;
}
