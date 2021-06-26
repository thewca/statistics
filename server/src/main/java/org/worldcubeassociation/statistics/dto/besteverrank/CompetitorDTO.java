package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

@Data
public class CompetitorDTO {
    private String wcaId;

    private String continent;

    private String country;

    private ResultsDTO single;

    private ResultsDTO average;
}
