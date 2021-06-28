package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class WorldDTO extends RegionDTO {
    @EqualsAndHashCode.Exclude
    private List<Competitor> competitors;

    public WorldDTO(String name) {
        super(name);
        this.competitors = new ArrayList<>();
    }
}
