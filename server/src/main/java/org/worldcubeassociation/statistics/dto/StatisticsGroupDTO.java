package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatisticsGroupDTO {
    @ApiModelProperty(value = "Group name", example = "Events")
    private String group;

    @ApiModelProperty("Statistics that belongs to this group")
    private List<ControlItemDTO> statistics;
}
