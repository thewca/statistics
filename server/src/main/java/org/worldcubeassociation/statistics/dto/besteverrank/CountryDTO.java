package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CountryDTO extends RegionDTO {
    @EqualsAndHashCode.Exclude
    private List<Competitor> competitors;

    public CountryDTO(String name) {
        super(name);
        this.competitors = new ArrayList<>();
    }
}
