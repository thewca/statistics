package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;

import java.time.LocalDate;
import java.util.List;

public interface RecordEvolutionService {
    void registerEvolution(List<RegionDTO> worlds, List<RegionDTO> continents, List<RegionDTO> countries, LocalDate date);

    void removeAll();

    List<EvolutionDto> getRecordEvolution(String region);
}
