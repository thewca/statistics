package org.worldcubeassociation.statistics.request.sumofranks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.worldcubeassociation.statistics.request.PageRequest;

@Data
@EqualsAndHashCode(callSuper = true)
public class SumOfRanksListRequest extends PageRequest {
    private String wcaId;
}
