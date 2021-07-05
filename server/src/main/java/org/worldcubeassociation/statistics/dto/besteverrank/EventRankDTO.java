package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // jackson
public class EventRankDTO {
    private EventDTO event;
    private List<CompetitorWorldDTO> worlds;
    private List<CompetitorContinentDTO> continents;
    private List<CompetitorCountryDTO> countries;

    public EventRankDTO(EventDTO event) {
        this.event = event;
        this.worlds = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.countries = new ArrayList<>();
    }
}
