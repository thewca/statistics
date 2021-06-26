package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

@Data
public class CompetitorWorldDTO {
    private String wcaId;
    private ResultsDTO single;
    private ResultsDTO average;
}
