package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDto {
    private String id;
    private String name;
    private Integer rank;
}
