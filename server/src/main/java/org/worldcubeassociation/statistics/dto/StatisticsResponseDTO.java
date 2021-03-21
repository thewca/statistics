package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponseDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @ApiModelProperty(value = "Statistics display title.", example = "Countries with most competitions.")
    private String title;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Number of competitions in each country sorted from the highest to the lowest.")
    private String explanation;


    @NotNull
    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Country\",\"Competitions\"]")
    private List<String> headers;

    @NotNull
    private List<StatisticsGroupResponseDTO> statistics;

    @ApiParam(allowableValues = "DEFAULT, SELECTOR")
    @Pattern(regexp = "^(DEFAULT|SELECTOR)")
    @ApiModelProperty(
            value = "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.",
            example = "DEFAULT")
    @NotBlank
    private String displayMode;
}
