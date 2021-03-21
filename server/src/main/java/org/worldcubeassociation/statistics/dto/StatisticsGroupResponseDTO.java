package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsGroupResponseDTO {
    @NotNull
    @ApiModelProperty(value = "Group identifier", example = "[\"2010\"], [\"Brazil\", \"2010\"]")
    private List<@NotBlank String> keys;

    @ApiModelProperty(value = "Grouped statistics explanation", example = "Explanation for this specific key")
    private String explanation;

    @NotNull
    @ApiModelProperty("Grouped statistics content")
    private List<@NotNull List<@NotNull String>> content;
}
