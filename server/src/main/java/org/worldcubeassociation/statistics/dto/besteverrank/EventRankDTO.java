package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.util.List;

@Data
public class EventRankDTO {
    private EventDTO event;
    private List<CompetitorDTO> worlds;
    private List<CompetitorDTO> continents;
    private List<CompetitorDTO> countries;
}
