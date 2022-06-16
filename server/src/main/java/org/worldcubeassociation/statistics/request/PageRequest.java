package org.worldcubeassociation.statistics.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageRequest {
    @Min(0)
    int page = 0;

    @Min(1)
    @Max(100)
    int pageSize = 20;
}
