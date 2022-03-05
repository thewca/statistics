package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.dto.recordevolution.EvolutionDto;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.repository.RecordEvolutionRepository;
import org.worldcubeassociation.statistics.service.RecordEvolutionService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class RecordEvolutionServiceImpl implements RecordEvolutionService {
    private final RecordEvolutionRepository repository;

    public RecordEvolutionServiceImpl(RecordEvolutionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerEvolution(List<RegionDTO> worlds, List<RegionDTO> continents, List<RegionDTO> countries, LocalDate date) {
        repository.upsert(worlds.get(0), date);
    }

    @Override
    public void removeAll() {
        log.info("Delete all existing record evolution");
        int deleted = repository.removeAll();
        log.info("{} results deleted", deleted);
    }

    @Override
    public List<EvolutionDto> getRecordEvolution(String region) {
        return repository.findById(region).orElseThrow(() -> new NotFoundException("No record evolution with region " + region)).getEvolution();

    }
}
