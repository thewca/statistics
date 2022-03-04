package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;

import java.util.List;

@RequestMapping("record-evolution")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface RecordEvolutionController {
    @GetMapping("{region}")
    List<EvolutionDto> getRecordEvolution(String region);
}
