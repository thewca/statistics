package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants(asEnum = true)
public class CompetitorCountryDTO implements Comparable<CompetitorCountryDTO> {
    private String wcaId;
    private String competition;
    private ResultsDTO single;
    private ResultsDTO average;
    private String continent;
    private String country;

    @Override
    public int compareTo(CompetitorCountryDTO o) {
        if (!wcaId.equals(o.getWcaId())) {
            return wcaId.compareTo(o.getWcaId());
        }

        if (!continent.equals(o.getContinent())) {
            return continent.compareTo(o.getContinent());
        }

        return country.compareTo(o.getCountry());
    }
}
