package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ControlItemDTO {
    private String title;
    private String path;

    @JsonIgnore
    private String group;
}
