package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
@EqualsAndHashCode(callSuper = true)
public class CompetitorCountryDTO extends CompetitorContinentDTO implements Comparable<Competitor>, Competitor {
    private String country;

    public CompetitorCountryDTO(CompetitorCountryDTO competitorCountryDTO) {
        super(competitorCountryDTO);
    }

    @Override
    public int compareTo(Competitor competitor) {
        CompetitorCountryDTO o = (CompetitorCountryDTO) competitor;
        if (!wcaId.equals(o.getWcaId())) {
            return wcaId.compareTo(o.getWcaId());
        }

        if (!continent.equals(o.getContinent())) {
            return continent.compareTo(o.getContinent());
        }

        return country.compareTo(o.getCountry());
    }
}
