package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.repository.BestEverRanksRepository;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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
    public BestEverRanksResponse generate(BestEverRanksRequest bestEverRanksRequest) {
        log.info("{} events to generate", bestEverRanksRequest.getEventIds().size());

        BestEverRanksResponse bestEverRanksResponse = new BestEverRanksResponse();

        for (String eventId : bestEverRanksRequest.getEventIds()) {
            generateByEventId(eventId, bestEverRanksResponse);
        }

        return bestEverRanksResponse;
    }

    private void generateByEventId(String eventId, BestEverRanksResponse bestEverRanksResponse) {
        log.info("Current event: {}", eventId);

        List<LocalDate> dates = bestEverRanksRepository.getDates(eventId);
        log.info("Found {} dates", dates.size());
    }

    @Override
    public BestEverRanksResponse generateAll() {
        return null;
    }
}
