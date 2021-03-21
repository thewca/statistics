package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class StatisticsResponseDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @ApiModelProperty(value = "Statistics display title.", example = "Competitors with most rounds")
    private String title;

    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Name\",\"Country\",\"Count\"]")
    private List<String> headers;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Competitors with most rounds considering first round, second round, finals and so on. Note: "
                    + "FMC and MBLD BO2, BO3 or MO3 count as 1.")
    private String explanation;

    private List<StatisticsGroupResponseDTO> statistics;

    @ApiParam(allowableValues = "DEFAULT, SELECTOR")
    @ApiModelProperty(
            "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.")
    private String displayMode;
}
