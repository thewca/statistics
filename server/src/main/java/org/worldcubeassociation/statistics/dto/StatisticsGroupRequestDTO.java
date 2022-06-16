package org.worldcubeassociation.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatisticsGroupRequestDTO extends StatisticsGroupBaseDTO {
    @Schema(title = "Group identifier", example = "[\"2010\"], [\"Brazil\", \"2010\"]")
    private List<@NotBlank String> keys;

    @Schema(title = "An optional value to \"keys\". Select a column to be treated as key so you won't have to write "
            + "multiple related query",
            example = "0")
    private Integer keyColumnIndex;

    @NotBlank
    @Schema(title = "Query to be grouped",
            example = "select countryId, count(*) qt from Competitions where year = 2010 group by countryId order by "
                    + "qt desc")
    private String sqlQuery;

    @Schema(
            title = "The same query but filtered somehow (example by user, country or competition) with a replaceable"
                    + " placeholder for finding a specific result",
            example =
                    "select countryId, count(*) qt from Competitions where countryId = ':COUNTRY_ID' group by "
                            + "countryId "
                            + "order by qt desc")
    private String sqlQueryCustom;

    @Schema(title = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Country\",\"Competitions\"]")
    private List<String> headers;

    @Schema(title = "Tell UI to automatically show positions", example = "true")
    private Boolean showPositions;

    @Schema(hidden = true)
    @AssertTrue(message = "You must inform exactly 1 of the following: keyColumnIndex or keys")
    public boolean isValid() {
        return (keyColumnIndex == null && keys.size() > 0) || (keyColumnIndex != null && keys.size() == 0);
    }
}
