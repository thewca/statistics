package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.dto.recordevolution.RecordEvolutionDto;

import java.time.LocalDate;

public interface RecordEvolutionService {
    void registerEvolution(RegionDTO world, String eventId, LocalDate date);

    void removeAll();

    RecordEvolutionDto getRecordEvolution(String region);
}
