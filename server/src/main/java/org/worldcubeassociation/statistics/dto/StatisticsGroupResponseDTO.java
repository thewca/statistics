package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsGroupResponseDTO {
    @ApiModelProperty("Group identifier")
    private String key;

    @ApiModelProperty("Grouped statistics explanation")
    private String explanation;

    @ApiModelProperty("Grouped statistics content")
    private List<List<String>> content;
}
