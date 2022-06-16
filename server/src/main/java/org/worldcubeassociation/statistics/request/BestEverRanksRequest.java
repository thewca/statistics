package org.worldcubeassociation.statistics.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldNameConstants(asEnum = true)
public class BestEverRanksRequest {

    @Size(min = 1)
    @Schema(title = "Event list to generate best ever ranks", example = "['333', '333fm']")
    private List<@NotBlank String> eventIds;
}
