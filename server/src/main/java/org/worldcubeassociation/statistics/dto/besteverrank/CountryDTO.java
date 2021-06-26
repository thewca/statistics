package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CountryDTO extends RegionDTO {
    private List<CompetitorCountryDTO> competitors;

    public CountryDTO(String name) {
        super(name);
    }
}
