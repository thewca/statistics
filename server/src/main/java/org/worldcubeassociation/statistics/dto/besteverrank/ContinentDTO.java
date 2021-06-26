package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContinentDTO extends RegionDTO {
    private List<CompetitorContinentDTO> competitors;

    public ContinentDTO(String name) {
        super(name);
    }
}
