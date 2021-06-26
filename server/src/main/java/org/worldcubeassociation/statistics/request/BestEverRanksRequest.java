package org.worldcubeassociation.statistics.request;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants(asEnum = true)
public class BestEverRanksRequest {
    private String eventId;
}
