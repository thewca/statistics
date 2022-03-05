package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.recordevolution.RecordEvolutionDto;

@RequestMapping("record-evolution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface RecordEvolutionController {
    @GetMapping("{eventId}")
    RecordEvolutionDto getRecordEvolution(@PathVariable String eventId);
}
