package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import javax.validation.constraints.NotBlank;

@Data
public class StatisticsRequestDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @ApiModelProperty("Statistics display title.")
    private String title;

    @ApiModelProperty("Explanation about the current statistic.")
    private String explanation;

    @NotBlank
    @ApiModelProperty("Custom table headers. If none is provided, it will default to the SQL columns response.")
    private List<String> headers;

    @ApiModelProperty("Query used to generate a statistic. Either provide this or sqlQueries.")
    private String sqlQuery;

    @ApiModelProperty(
            "Groups statistics results by key. Example: you can use {'single': 'query for single', 'average', 'query "
                    + "for average'}. Either provide this or sqlQuery")
    private LinkedHashMap<String, StatisticsGroupRequestDTO> sqlQueries;

    @Data
    private static class StatisticsGroupRequestDTO {
        @NotBlank
        private String sqlQuery;
        private String explanation;
    }
}
