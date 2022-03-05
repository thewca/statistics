package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.RecordEvolutionController;
import org.worldcubeassociation.statistics.dto.EventDto;
import org.worldcubeassociation.statistics.dto.recordevolution.RecordEvolutionDto;
import org.worldcubeassociation.statistics.service.RecordEvolutionService;

import java.util.List;

@RestController
public class RecordEvolutionControllerImpl implements RecordEvolutionController {
    private final RecordEvolutionService recordEvolutionService;

    public RecordEvolutionControllerImpl(RecordEvolutionService recordEvolutionService) {
        this.recordEvolutionService = recordEvolutionService;
    }

    @Override
    public RecordEvolutionDto getRecordEvolution(String eventId) {
        return recordEvolutionService.getRecordEvolution(eventId);
    }

    @Override
    public List<EventDto> getAvailableEvents() {
        return recordEvolutionService.getAvailableEvents();
    }
}
