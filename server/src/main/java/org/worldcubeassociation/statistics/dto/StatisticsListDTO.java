package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsListDTO {
    private List<StatisticsGroupDTO> list;

    @ApiModelProperty("Number of groups")
    public Integer getGroups() {
        return list.size();
    }

    @ApiModelProperty("Number of statistics")
    public Integer getTotalSize() {
        return list.stream().map(StatisticsGroupDTO::getSize).reduce(0, Integer::sum);
    }
}
