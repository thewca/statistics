package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class StatisticsGroupBaseDTO {
    @ApiModelProperty(
            value = "If UI is displaying positions, some results should be considered as a tie. By setting this "
                    + "index, you can control which column decides tie. If two results tie, let's say in position 2, "
                    + "the first listed result receives '2' as position and the other one receives '-', tie sign. The"
                    + " index is 0-based and you can use column index as it appears in the header.",
            example = "3")
    private Integer positionTieBreakerIndex;

    @ApiModelProperty(value = "Grouped query purpose", example = "Competitions in 2010")
    private String explanation;
}
