package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotBlank;

@Data
public class StatisticsGroupRequestDTO {
    @NotBlank
    @ApiModelProperty(value = "Group identifier", example = "[\"2010\"], [\"Brazil\", \"2010\"]")
    private List<@NotBlank String> keys;

    @NotBlank
    @ApiModelProperty(value = "Query to be grouped",
            example = "select countryId, count(*) qt from Competitions where year = 2010 group by countryId order by "
                    + "qt desc")
    private String sqlQuery;

    @ApiModelProperty(
            value = "The same query but filtered somehow (example by user, country or competition) with a replaceable"
                    + " placeholder for finding a specific result",
            example = "select countryId, count(*) qt from Competitions where countryId = '%s' group by countryId "
                    + "order by qt desc")
    private String sqlQueryCustom;

    @ApiModelProperty(value = "Grouped query purpose", example = "Competitions in 2010")
    private String explanation;
}
