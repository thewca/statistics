package org.worldcubeassociation.statistics.dto.recordevolution;

import lombok.Data;

@Data
public class EvolutionDto {
    private String name;
    private Integer best1;
    private Integer best10;
    private Integer best1000;
}
