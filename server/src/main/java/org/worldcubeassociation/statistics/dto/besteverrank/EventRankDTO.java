package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventRankDTO {
    private EventDTO event;
    private List<Competitor> worlds;
    private List<Competitor> continents;
    private List<Competitor> countries;

    public EventRankDTO(EventDTO event) {
        this.event = event;
        this.worlds = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.countries = new ArrayList<>();
    }
}
