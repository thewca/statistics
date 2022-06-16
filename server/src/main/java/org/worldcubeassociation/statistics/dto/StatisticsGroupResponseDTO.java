package org.worldcubeassociation.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsGroupResponseDTO extends StatisticsGroupBaseDTO {
    @NotNull
    @Schema(title = "Group identifier", example = "[\"2010\"], [\"Brazil\", \"2010\"]")
    private List<@NotBlank String> keys;

    @Schema(
            title = "The same query but filtered somehow (example by user, country or competition) with a replaceable"
                    + " placeholder for finding a specific result",
            example =
                    "select countryId, count(*) qt from Competitions where countryId = ':COUNTRY_ID' group by "
                            + "countryId "
                            + "order by qt desc")
    private String sqlQueryCustom;

    @NotNull
    @Size(max = 100) // Competitions has over 60 columns
    @Schema(title = "Custom table headers. If none is provided, it will default to the SQL columns response.",
            example = "[\"Country\",\"Competitions\"]")
    private List<String> headers;

    @NotNull
    @Schema(title = "Tell UI to automatically show positions", example = "true")
    private Boolean showPositions;

    @NotNull
    @Size(max = 300) // Almost 200 countries, so maybe 300 makes sense
    @Schema(title = "Grouped statistics content")
    private List<@NotNull List<@NotNull String>> content;
}
