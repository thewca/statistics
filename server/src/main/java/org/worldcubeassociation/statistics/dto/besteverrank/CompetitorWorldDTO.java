package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
public class CompetitorWorldDTO implements Competitor {
    protected String wcaId;
    private String competition;
    private ResultsDTO single;
    private ResultsDTO average;

    public CompetitorWorldDTO(CompetitorCountryDTO competitorCountryDTO) {
        this.wcaId = competitorCountryDTO.getWcaId();
        this.single = new ResultsDTO(competitorCountryDTO.getSingle().getCurrent().getResult(), competitorCountryDTO.getSingle().getCurrent().getCompetition(), competitorCountryDTO.getSingle().getCurrent().getStart());
        this.average = new ResultsDTO(competitorCountryDTO.getAverage().getCurrent().getResult(), competitorCountryDTO.getAverage().getCurrent().getCompetition(), competitorCountryDTO.getAverage().getCurrent().getStart());
    }

    @Override
    public int compareTo(Competitor o) {
        return wcaId.compareTo(o.getWcaId());
    }
}
