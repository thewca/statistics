package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsGroupResponseDTO {
    @NotNull
    @ApiModelProperty(value = "Group identifier", example = "[\"2010\"], [\"Brazil\", \"2010\"]")
    private List<@NotBlank String> keys;

    @ApiModelProperty(value = "Grouped statistics explanation", example = "Explanation for this specific key")
    private String explanation;

    @ApiModelProperty(
            value = "The same query but filtered somehow (example by user, country or competition) with a replaceable"
                    + " placeholder for finding a specific result",
            example = "select countryId, count(*) qt from Competitions where countryId = ':COUNTRY_ID' group by countryId "
                    + "order by qt desc")
    private String sqlQueryCustom;

    @NotNull
    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Country\",\"Competitions\"]")
    private List<String> headers;

    @NotNull
    @ApiModelProperty("Grouped statistics content")
    private List<@NotNull List<@NotNull String>> content;
}
