package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class CompetitorContinentDTO extends CompetitorWorldDTO implements Comparable<Competitor>, Competitor {
    protected String continent;

    public CompetitorContinentDTO(CompetitorCountryDTO competitorCountryDTO) {
        super(competitorCountryDTO);
        this.continent = competitorCountryDTO.getContinent();
    }

    @Override
    public int compareTo(Competitor competitor) {
        CompetitorContinentDTO o = (CompetitorContinentDTO) competitor;
        if (!wcaId.equals(o.getWcaId())) {
            return wcaId.compareTo(o.getWcaId());
        }

        return continent.compareTo(o.getContinent());
    }
}
