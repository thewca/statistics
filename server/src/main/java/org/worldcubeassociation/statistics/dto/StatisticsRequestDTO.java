package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import javax.validation.constraints.NotBlank;

@Data
public class StatisticsRequestDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @ApiModelProperty(value = "Statistics display title.", example = "Competitors with most rounds")
    private String title;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Competitors with most rounds considering first round, second round, finals and so on. Node: "
                    + "FMC and MBLD BO2 or BO3 count as 1.")
    private String explanation;

    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Name\",\"Country\",\"Count\"]")
    private List<String> headers;

    @ApiModelProperty(value = "Query used to generate a statistic. Either provide this or sqlQueries.", example =
            "select personName, countryId, count(*) rounds from Results group by personName, countryId order by "
                    + "rounds desc")
    private String sqlQuery;

    @ApiModelProperty(
            "Groups statistics results by key. Example: you can use {'single': 'query for single', 'average', 'query "
                    + "for average'}. Either provide this or sqlQuery")
    private LinkedHashMap<String, StatisticsGroupRequestDTO> sqlQueries;

    @ApiParam(allowableValues = "DEFAULT, SELECTOR")
    @ApiModelProperty(
            "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.")
    private String displayMode;

    @Data
    private static class StatisticsGroupRequestDTO {
        @NotBlank
        @ApiModelProperty("select * from ... where countryId = 'country1'")
        private String sqlQuery;

        @ApiModelProperty("Result for country 1")
        private String explanation;
    }
}
