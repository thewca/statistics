package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.*;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.repository.BestEverRanksRepository;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

        List<WorldDTO> worlds = new ArrayList<>();
        List<ContinentDTO> continents = new ArrayList<>();
        List<CountryDTO> countries = new ArrayList<>();

        worlds.add(new WorldDTO("world"));

        for (LocalDate date : dates) {
            List<CompetitorCountryDTO> todayCompetitors = bestEverRanksRepository.getTodayCompetitors(date, eventId);

            summarizeResults(todayCompetitors, worlds, continents, countries, date);
        }
    }

    private void summarizeResults(List<CompetitorCountryDTO> todayCompetitors, List<WorldDTO> worlds, List<ContinentDTO> continents, List<CountryDTO> countries, LocalDate today) {
        WorldDTO world = worlds.get(0);

        // Asign today's best result
        for (CompetitorCountryDTO todayCompetitor : todayCompetitors) {
            CompetitorWorldDTO competitorWorld = new CompetitorWorldDTO(todayCompetitor);
            updateResults(world, competitorWorld, today);
        }
    }

    private void updateResults(RegionDTO region, Competitor todayCompetitor, LocalDate today) {
        Competitor regionCompetitor = findOrCreateCompetitor(region, todayCompetitor);

        // Singles are always non null
        if (todayCompetitor.getSingle().getCurrent().getResult() < regionCompetitor.getSingle().getCurrent().getResult()){
            // We remove the old result and insert the new one

            int index = Collections.binarySearch(region.getSingles(), regionCompetitor.getSingle().getCurrent().getResult());
            region.getSingles().remove(index);
        }

    }

    private Competitor findOrCreateCompetitor(Region region, Competitor competitor) {
        List<Competitor> competitors = region.getCompetitors();
        int index = Collections.binarySearch(competitors, competitor);
        if (index < 0) {
            // In case then competitor is not there, we add it
            index = -index - 1;
            competitors.add(-index - 1, competitor);

            // Also in the single list
            Integer single = competitor.getSingle().getCurrent().getResult();
            int j = Collections.binarySearch(region.getSingles(), single);
            if (j < 0) {
                region.getSingles().add(-j - 1, single);
            } else {
                region.getSingles().add(j, single);
            }
        }
        return competitors.get(index);
    }

    @Override
    public BestEverRanksResponse generateAll() {
        return null;
    }
}
