package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class StatisticsResponseDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @ApiModelProperty("Statistics display title.")
    private String title;

    @ApiModelProperty("Explanation about the current statistic.")
    private String explanation;

    private LinkedHashMap<String, StatisticsGroupResponseDTO> statistics;

    @ApiModelProperty("Tables' headers.")
    private List<String> headers;

    @Data
    private static class StatisticsGroupResponseDTO {
        @ApiModelProperty("Grouped statistics explanation")
        private String explanation;

        @ApiModelProperty("Grouped statistics content")
        private List<List<String>> content;

    }
}
