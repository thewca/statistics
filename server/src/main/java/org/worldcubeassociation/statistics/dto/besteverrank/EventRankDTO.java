package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.worldcubeassociation.statistics.dto.EventDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // jackson
public class EventRankDTO {
    private EventDto event;
    private List<CompetitorWorldDTO> worlds;
    private List<CompetitorContinentDTO> continents;
    private List<CompetitorCountryDTO> countries;

    public EventRankDTO(EventDto event) {
        this.event = event;
        this.worlds = new ArrayList<>();
        this.continents = new ArrayList<>();
        this.countries = new ArrayList<>();
    }
}
