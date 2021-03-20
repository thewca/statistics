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

    @ApiModelProperty(value = "Statistics display title.", example = "Competitors with most rounds")
    private String title;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Competitors with most rounds considering first round, second round, finals and so on. Node: "
                    + "FMC and MBLD BO2 or BO3 count as 1.")
    private String explanation;

    private LinkedHashMap<String, StatisticsGroupResponseDTO> statistics;

    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Name\",\"Country\",\"Count\"]")
    private List<String> headers;


    private String displayMode;

    @Data
    private static class StatisticsGroupResponseDTO {
        @ApiModelProperty("Grouped statistics explanation")
        private String explanation;

        @ApiModelProperty("Grouped statistics content")
        private List<List<String>> content;

    }
}
