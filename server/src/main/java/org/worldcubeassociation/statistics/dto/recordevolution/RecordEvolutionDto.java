package org.worldcubeassociation.statistics.dto.recordevolution;

import lombok.Data;

import java.util.List;

@Data
public class RecordEvolutionDto {
    private String eventId;
    private List<EvolutionDto> evolution;
}
