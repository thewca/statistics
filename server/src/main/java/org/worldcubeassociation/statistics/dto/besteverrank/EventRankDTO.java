package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.util.List;

@Data
public class EventRankDTO {
    private EventDTO event;
    private List<Competitor> worlds;
    private List<Competitor> continents;
    private List<Competitor> countries;
}
