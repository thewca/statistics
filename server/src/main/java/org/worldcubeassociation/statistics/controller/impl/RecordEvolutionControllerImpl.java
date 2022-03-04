package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.RecordEvolutionController;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;
import org.worldcubeassociation.statistics.service.RecordEvolutionService;

import java.util.List;

@RestController
public class RecordEvolutionControllerImpl implements RecordEvolutionController {
    private final RecordEvolutionService recordEvolutionService;

    public RecordEvolutionControllerImpl(RecordEvolutionService recordEvolutionService) {
        this.recordEvolutionService = recordEvolutionService;
    }

    @Override
    public List<EvolutionDto> getRecordEvolution(String region) {
        return recordEvolutionService.getRecordEvolution(region);
    }
}
