package org.worldcubeassociation.statistics.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatisticsGroupRequestDTO extends StatisticsGroupBaseDTO {
    @ApiModelProperty(value = "Group identifier", example = "[\"2010\"], [\"Brazil\", \"2010\"]")
    private List<@NotBlank String> keys;

    @ApiModelProperty(value = "An optional value to \"keys\". Select a column to be treated as key so you won't have to write multiple related query", example = "0")
    private Integer keyColumnIndex;

    @NotBlank
    @ApiModelProperty(value = "Query to be grouped",
            example = "select countryId, count(*) qt from Competitions where year = 2010 group by countryId order by "
                    + "qt desc")
    private String sqlQuery;

    @ApiModelProperty(
            value = "The same query but filtered somehow (example by user, country or competition) with a replaceable"
                    + " placeholder for finding a specific result",
            example =
                    "select countryId, count(*) qt from Competitions where countryId = ':COUNTRY_ID' group by "
                            + "countryId "
                            + "order by qt desc")
    private String sqlQueryCustom;

    @ApiModelProperty(value = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Country\",\"Competitions\"]")
    private List<String> headers;

    @ApiModelProperty(value = "Tell UI to automatically show positions", example = "true")
    private Boolean showPositions;

    @ApiParam(hidden = true)
    @AssertTrue(message = "You must inform exactly 1 of the following: keyColumnIndex or keys")
    public boolean isValid() {
        return (keyColumnIndex == null && keys.size() > 0) || (keyColumnIndex != null && keys.size() == 0);
    }
}
