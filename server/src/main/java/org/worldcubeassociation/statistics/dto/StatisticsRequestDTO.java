package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class StatisticsRequestDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @ApiModelProperty(value = "Statistics display title.", example = "Competitors with most rounds")
    private String title;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Competitors with most rounds considering first round, second round, finals and so on. Note: "
                    + "FMC and MBLD BO2, BO3 or MO3 count as 1.")
    private String explanation;

    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Name\",\"Country\",\"Count\"]")
    private List<String> headers;

    @ApiModelProperty(value = "Query used to generate a statistic. Either provide this or sqlQueries.", example =
            "select personName, countryId, count(*) rounds from Results group by personName, countryId order by "
                    + "rounds desc limit 10")
    private String sqlQuery;

    @ApiModelProperty(
            "Groups statistics results by key. Example: you can use [{'key': 'single', 'explanation': 'Results for "
                    + "single', 'sqlQuery': 'select * from...'}, {'key': 'average', 'explanation': 'Results for "
                    + "average', 'sqlQuery': 'select * from ...'}]")
    private List<StatisticsGroupRequestDTO> sqlQueries;

    @ApiParam(allowableValues = "DEFAULT, SELECTOR")
    @Pattern(regexp = "^(DEFAULT|SELECTOR)")
    @ApiModelProperty(
            "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.")
    private String displayMode;
}
