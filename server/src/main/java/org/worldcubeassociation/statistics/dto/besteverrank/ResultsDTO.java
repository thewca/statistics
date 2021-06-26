package org.worldcubeassociation.statistics.dto.besteverrank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultsDTO {
    private ResultDTO current;
    private ResultDTO bestRank;
}
