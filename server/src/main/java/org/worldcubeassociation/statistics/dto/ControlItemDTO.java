package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // @see StatisticsRepository
public class ControlItemDTO {
    private String path;
    private String title;

    // Used just for grouping, but it show not be shown.
    @JsonIgnore
    private String groupName;
}
