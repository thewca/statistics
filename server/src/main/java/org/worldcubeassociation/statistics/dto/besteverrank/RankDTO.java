package org.worldcubeassociation.statistics.dto.besteverrank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class RankDTO {
    private ResultDTO single;

    private ResultDTO average;

    @JsonProperty("event_id")
    private String event;

    @JsonProperty("wca_id")
    private String wcaId;

    private String country;

    private String continent;

    @JsonProperty("best_world_single_rank")
    private ResultDTO bestWorldSingleRank;

    @JsonProperty("best_world_average_rank")
    private ResultDTO bestWorldAverageRank;

    @JsonProperty("best_country_single_rank")
    private ResultDTO bestCountrySingleRank;

    @JsonProperty("best_country_average_rank")
    private ResultDTO bestCountryAverageRank;

    @JsonProperty("best_continent_single_rank")
    private ResultDTO bestContinentSingleRank;

    @JsonProperty("best_continent_average_rank")
    private ResultDTO bestContinentAverageRank;
}
