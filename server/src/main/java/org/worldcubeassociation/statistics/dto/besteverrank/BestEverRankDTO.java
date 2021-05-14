package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BestEverRankDTO {
    private String personId;

    private LocalDateTime lastModified;

    private List<EventRankDTO> eventRanks;
}
