package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.repository.BestEverRanksRepository;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

@Service
public class BestEverRanksServiceImpl implements BestEverRanksService {
    @Autowired
    private BestEverRanksRepository bestEverRanksRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BestEverRankDTO get(String personId) {
        BestEverRank bestEverRank = bestEverRanksRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Rank not found for " + personId));
        return objectMapper.convertValue(bestEverRank, BestEverRankDTO.class);
    }

    @Override
    public BestEverRanksResponse generate(String eventId) {
        return null;
    }

    @Override
    public BestEverRanksResponse generate() {
        return null;
    }
}
