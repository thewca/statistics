package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StatisticsGroupRequestDTO {
    @NotBlank
    @ApiModelProperty(value = "Group identifier", example = "2010")
    private String key;

    @NotBlank
    @ApiModelProperty(value = "Query to be grouped",
            example = "select countryId, count(*) qt from Competitions where year = 2010 group by countryId order by qt desc")
    private String sqlQuery;

    @ApiModelProperty(value = "Grouped query purpose", example = "Competitions in 2010")
    private String explanation;
}
