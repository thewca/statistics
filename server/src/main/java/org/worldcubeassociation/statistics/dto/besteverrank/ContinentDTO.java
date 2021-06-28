package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContinentDTO extends RegionDTO {
    @EqualsAndHashCode.Exclude
    private List<Competitor> competitors;

    public ContinentDTO(String name) {
        super(name);
        this.competitors = new ArrayList<>();
    }
}
