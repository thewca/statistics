package org.worldcubeassociation.statistics.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@FieldNameConstants(asEnum = true)
public class BestEverRanksRequest {

    @Size(min = 1)
    @Schema(title = "Event list to generate best ever ranks", example = "['333', '333fm']")
    private List<@NotBlank String> eventIds;
}
