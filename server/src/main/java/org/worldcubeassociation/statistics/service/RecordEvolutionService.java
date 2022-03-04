package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;

import java.time.LocalDate;

public interface RecordEvolutionService {
    void registerEvolution(RegionDTO region, LocalDate today);
}
