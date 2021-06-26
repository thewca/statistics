package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class WorldDTO extends RegionDTO {
    private List<CompetitorWorldDTO> competitors;

    public WorldDTO(String name) {
        super(name);
    }
}
