package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @ApiModelProperty(value = "Statistics display title.", example = "Countries with most competitions")
    private String title;

    @ApiModelProperty(value = "Explanation about the current statistic.",
            example = "Number of competitions in each country sorted from the highest to the lowest.")
    private String explanation;

    @NotNull
    private List<@Valid StatisticsGroupResponseDTO> statistics;

    @NotNull
    @ApiModelProperty(
            value = "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.",
            example = "DEFAULT")
    private DisplayModeEnum displayMode;

    @NotBlank
    @ApiModelProperty(value = "Group statistics for better searching/navigating")
    private String groupName;
}
