package org.worldcubeassociation.statistics.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayModeEnum {
    @Schema(title = "Displays all statistics")
    DEFAULT(0),

    @Schema(title =
            "Let the user to select statistics to show based on the value of a selector. Selector value must match "
                    + "statistics key.")
    SELECTOR(1),

    @Schema(title =
            "In case of statistics with multiple keys, user can select keys to show them grouped. E.g. [3x3x3, sub 5]"
                    + ". [3x3x3, sub 8], [2x2x2, sub 3]. can be grouped as [[3x3x3, sub 5], [3x3x3], sub 8] and "
                    + "[2x2x2, sub 3]")
    GROUPED(2);

    private Integer vallue;
}
