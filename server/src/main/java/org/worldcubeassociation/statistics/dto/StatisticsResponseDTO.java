package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponseDTO extends StatisticsDTO {
    @NotBlank
    @ApiParam(value = "Path to the current statistics", example = "countries-with-most-competitions")
    private String path;

    @NotNull
    @ApiParam("Statistics computed at")
    private LocalDateTime lastModified;
}
