package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@FieldNameConstants(asEnum = true)
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
