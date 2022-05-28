package org.worldcubeassociation.statistics.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DatabaseQueryMetaResponse {
    private LocalDate exportDate;
    private String additionalInformation;
}
