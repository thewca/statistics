package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StatisticsGroupRequestDTO {
    @ApiModelProperty("Group identifier")
    private String key;

    @NotBlank
    @ApiModelProperty("select * from ... where countryId = 'country1'")
    private String sqlQuery;

    @ApiModelProperty("Result for country 1")
    private String explanation;
}
