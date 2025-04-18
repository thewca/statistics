package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponseDTO extends StatisticsDTO {
    @NotBlank
    @Schema(title = "Path to the current statistics", example = "countries-with-most-competitions")
    private String path;

    @NotNull
    @Schema(title = "Statistics computed at")
    private LocalDateTime lastModified;

    @NotNull
    @Schema(title = "An approximation for the latest result considered")
    private LocalDateTime exportDate;
}
