package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompetitorCountryDTO extends CompetitorContinentDTO {
    private String country;
}
