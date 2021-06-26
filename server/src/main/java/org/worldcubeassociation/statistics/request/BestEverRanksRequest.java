package org.worldcubeassociation.statistics.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@FieldNameConstants(asEnum = true)
public class BestEverRanksRequest {

    @Size(min = 1)
    @ApiModelProperty(value = "Event list to generate best ever ranks", example = "['333', '333fm']")
    private List<@NotBlank String> eventIds;
}
