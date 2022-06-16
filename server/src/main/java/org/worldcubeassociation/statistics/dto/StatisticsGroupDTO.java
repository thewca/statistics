package org.worldcubeassociation.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatisticsGroupDTO {
    @Schema(title = "Group name", example = "Events")
    private String group;

    @Schema(title = "Statistics that belongs to this group")
    private List<ControlItemDTO> statistics;

    @Schema(title = "Number of statistics in the current group")
    public Integer getSize() {
        return statistics.size();
    }
}
