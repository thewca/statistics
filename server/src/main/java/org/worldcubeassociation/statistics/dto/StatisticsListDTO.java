package org.worldcubeassociation.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsListDTO {
    private List<StatisticsGroupDTO> list;

    @Schema(title = "Number of groups")
    public Integer getGroups() {
        return list.size();
    }

    @Schema(title = "Number of statistics")
    public Integer getTotalSize() {
        return list.stream().map(StatisticsGroupDTO::getSize).reduce(0, Integer::sum);
    }
}
