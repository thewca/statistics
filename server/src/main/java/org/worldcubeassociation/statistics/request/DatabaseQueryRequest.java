package org.worldcubeassociation.statistics.request;

import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class DatabaseQueryRequest {

    @Min(0)
    private Integer page = 0;

    @Min(1) @Max(100)
    private Integer size = 20;

    @NotBlank
    private String sqlQuery;
}
