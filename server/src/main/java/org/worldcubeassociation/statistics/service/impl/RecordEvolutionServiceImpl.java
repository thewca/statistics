package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.dto.recordevolution.RecordEvolutionDto;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.RecordEvolution;
import org.worldcubeassociation.statistics.repository.RecordEvolutionRepository;
import org.worldcubeassociation.statistics.service.RecordEvolutionService;

import java.time.LocalDate;

@Slf4j
@Service
public class RecordEvolutionServiceImpl implements RecordEvolutionService {
    private final RecordEvolutionRepository repository;
    private final ObjectMapper objectMapper;

    public RecordEvolutionServiceImpl(RecordEvolutionRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerEvolution(RegionDTO world, String eventId, LocalDate date) {
        repository.upsert(world, eventId, date);
    }

    @Override
    public void removeAll() {
        log.info("Delete all existing record evolution");
        int deleted = repository.removeAll();
        log.info("{} results deleted", deleted);
    }

    @Override
    public RecordEvolutionDto getRecordEvolution(String eventId) {
        RecordEvolution recordEvolution = repository.findById(eventId).orElseThrow(() -> new NotFoundException("No record evolution with event " + eventId));
        return objectMapper.convertValue(recordEvolution, RecordEvolutionDto.class);
    }
}
