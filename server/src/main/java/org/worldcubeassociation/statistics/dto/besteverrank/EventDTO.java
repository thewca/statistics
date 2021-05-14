package org.worldcubeassociation.statistics.dto.besteverrank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventDTO {
    @JsonProperty("event_id")
    private String eventId;
    private String name;
}
