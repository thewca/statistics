package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode // Only wcaId is considered
public class CompetitorWorldDTO {
    private String wcaId;

    @EqualsAndHashCode.Exclude
    private String competition;

    @EqualsAndHashCode.Exclude
    private Integer single;

    @EqualsAndHashCode.Exclude
    private Integer average;
}
