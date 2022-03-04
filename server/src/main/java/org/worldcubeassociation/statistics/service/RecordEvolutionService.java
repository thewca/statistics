package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;

import java.time.LocalDate;
import java.util.List;

public interface RecordEvolutionService {
    void registerEvolution(RegionDTO region, LocalDate today);

    void removeAll();

    List<EvolutionDto> getRecordEvolution(String region);
}
