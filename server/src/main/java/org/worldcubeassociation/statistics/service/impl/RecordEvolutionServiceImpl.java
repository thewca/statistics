package org.worldcubeassociation.statistics.service.impl;

import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.repository.RecordEvolutionRepository;
import org.worldcubeassociation.statistics.service.RecordEvolutionService;

import java.time.LocalDate;

@Service
public class RecordEvolutionServiceImpl implements RecordEvolutionService {
    private final RecordEvolutionRepository repository;

    public RecordEvolutionServiceImpl(RecordEvolutionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerEvolution(RegionDTO region, LocalDate today) {
        repository.upsert(region, today);
    }
}
