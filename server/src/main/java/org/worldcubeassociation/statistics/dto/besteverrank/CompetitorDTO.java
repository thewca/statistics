package org.worldcubeassociation.statistics.dto.besteverrank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompetitorDTO {
    @JsonProperty("wca_id")
    private String wcaId;

    private String continent;

    private String country;

    private ResultsDTO single;

    private ResultsDTO average;
}
