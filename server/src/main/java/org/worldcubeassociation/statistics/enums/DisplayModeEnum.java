package org.worldcubeassociation.statistics.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayModeEnum {
    @ApiModelProperty("Displays all statistics")
    DEFAULT(0),

    @ApiModelProperty(
            "Let the user to select statistics to show based on the value of a selector. Selector value must match "
                    + "statistics key.")
    SELECTOR(1),

    @ApiModelProperty(
            "In case of statistics with multiple keys, user can select keys to show them grouped. E.g. [3x3x3, sub 5]"
                    + ". [3x3x3, sub 8], [2x2x2, sub 3]. can be grouped as [[3x3x3, sub 5], [3x3x3], sub 8] and "
                    + "[2x2x2, sub 3]")
    GROUPED(2);

    private Integer vallue;
}
