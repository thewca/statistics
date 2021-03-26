package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data

public class StatisticsRequestDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @ApiModelProperty(value = "Statistics display title.", example = "Countries with most competitions.")
    private String title;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Number of competitions in each country sorted from the highest to the lowest.")
    private String explanation;

    @NotEmpty
    @ApiModelProperty(
            "Groups statistics results by key. Example: you can use [{'key': '2010', 'explanation': 'Competitions in "
                    + "2010', 'sqlQuery': 'select * from... where year = 2010'}, {'key': '2015', 'explanation': "
                    + "'Competitions in 2015', 'sqlQuery': 'select * from ... where year = 2015'}]")
    private List<@Valid StatisticsGroupRequestDTO> queries;

    @ApiParam(allowableValues = "DEFAULT, SELECTOR")
    @Pattern(regexp = "^(DEFAULT|SELECTOR)")
    @ApiModelProperty(
            value = "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.",
            example = "DEFAULT")
    private String displayMode;
}
