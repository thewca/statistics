package org.worldcubeassociation.statistics.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class DatabaseQueryRequest {

    @Min(0)
    private Integer page;

    @Min(1) @Max(100)
    private Integer size;

    @NotBlank
    private String sqlQuery;
}
