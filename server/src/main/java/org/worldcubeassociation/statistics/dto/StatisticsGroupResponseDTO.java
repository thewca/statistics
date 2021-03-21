package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsGroupResponseDTO {
    @ApiModelProperty(value = "Group identifier", example = "key1")
    private String key;

    @ApiModelProperty(value = "Grouped statistics explanation", example = "Explanation for this specific key")
    private String explanation;

    @NotNull
    @ApiModelProperty("Grouped statistics content")
    private List<@NotNull List<@NotNull String>> content;
}
