package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.worldcubeassociation.statistics.enums.DisplayModeEnum;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data

public class StatisticsRequestDTO {
    @JsonProperty("version")
    private static final String VERSION = "v1";

    @NotBlank
    @Schema(title = "Statistics display title.", example = "Countries with most competitions")
    private String title;

    @Schema(title = "Explanation about the current statistic.",
            example = "Number of competitions in each country sorted from the highest to the lowest.")
    private String explanation;

    @NotEmpty
    @Schema(title =
            "Groups statistics results by key. Example: you can use [{'key': '2010', 'explanation': 'Competitions in "
                    + "2010', 'sqlQuery': 'select * from... where year = 2010'}, {'key': '2015', 'explanation': "
                    + "'Competitions in 2015', 'sqlQuery': 'select * from ... where year = 2015'}]")
    private List<@Valid StatisticsGroupRequestDTO> queries;

    @Valid
    @Schema(
            title = "In case of grouped statistics, you can select DEFAULT to display all of them in the frontend or "
                    + "'SELECTOR' to group them in a selector.",
            example = "DEFAULT")
    private DisplayModeEnum displayMode;

    @NotBlank
    @Schema(title = "Group statistics for better searching/navigating")
    private String groupName;
}
