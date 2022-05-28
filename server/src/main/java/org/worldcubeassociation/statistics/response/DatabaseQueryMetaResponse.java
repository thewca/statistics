package org.worldcubeassociation.statistics.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DatabaseQueryMetaResponse {
    private LocalDateTime exportDate;
    private String additionalInformation;
}
