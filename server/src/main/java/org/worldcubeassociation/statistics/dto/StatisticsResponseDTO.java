package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponseDTO extends StatisticsDTO {
    @NotBlank
    @ApiParam(value = "Path to the current statistics", example = "countries-with-most-competitions")
    private String path;

    public StatisticsResponseDTO(StatisticsDTO statisticsDTO) {
        setStatistics(statisticsDTO.getStatistics());
        setTitle(statisticsDTO.getTitle());
        setDisplayMode(statisticsDTO.getDisplayMode());
        setExplanation(statisticsDTO.getExplanation());
    }
}
