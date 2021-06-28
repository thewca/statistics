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

        List<RegionDTO> worlds = new ArrayList<>();
        List<RegionDTO> continents = new ArrayList<>();
        List<RegionDTO> countries = new ArrayList<>();

        worlds.add(new RegionDTO("world"));

        for (LocalDate date : dates) {
            List<CompetitorCountryDTO> todayCompetitors = bestEverRanksRepository.getTodayCompetitors(date, eventId);

            summarizeResults(todayCompetitors, worlds, continents, countries, date);
        }
    }

    private void summarizeResults(List<CompetitorCountryDTO> todayCompetitors, List<RegionDTO> worlds, List<RegionDTO> continents, List<RegionDTO> countries, LocalDate today) {
        RegionDTO world = worlds.get(0);

        // Asign today's best result
        for (CompetitorCountryDTO todayCompetitor : todayCompetitors) {
            CompetitorWorldDTO competitorWorld = new CompetitorWorldDTO(todayCompetitor);
            updateResults(world, competitorWorld, today);
        }
    }

    private void updateResults(RegionDTO region, Competitor todayCompetitor, LocalDate today) {
        Competitor regionCompetitor = findOrCreateCompetitor(region, todayCompetitor);

        // Singles are always non null
        Integer oldSingle = regionCompetitor.getSingle().getCurrent().getResult();
        Integer newSingle = todayCompetitor.getSingle().getCurrent().getResult();
        if (newSingle < oldSingle) {
            // We remove the old result and insert the new one

            int index = Collections.binarySearch(region.getSingles(), oldSingle);
            region.getSingles().remove(index);

            int j = Collections.binarySearch(region.getSingles(), newSingle);
            region.getSingles().add(Math.max(-j - 1, j), newSingle);

            regionCompetitor.getSingle().setCurrent(todayCompetitor.getSingle().getCurrent());
        }

        Integer oldAverage = regionCompetitor.getAverage().getCurrent().getResult();
        Integer newAverage = todayCompetitor.getAverage().getCurrent().getResult();
        if (newAverage != null && (oldAverage == null || newAverage < oldAverage)) {
            int index = Collections.binarySearch(region.getAverages(), oldAverage);
            if (index >= 0) {
                region.getSingles().remove(index);
            }

            int j = Collections.binarySearch(region.getAverages(), newAverage);
            region.getAverages().add(Math.max(-j - 1, j), newAverage);

            regionCompetitor.getAverage().setCurrent(todayCompetitor.getAverage().getCurrent());

        }

    }

    private Competitor findOrCreateCompetitor(RegionDTO region, Competitor competitor) {
        List<Competitor> competitors = region.getCompetitors();
        int index = Collections.binarySearch(competitors, competitor);
        if (index < 0) {
            // In case then competitor is not there, we add it
            index = -index - 1;
            competitors.add(index, competitor);

            // Also in the single list
            Integer single = competitor.getSingle().getCurrent().getResult();
            int j = Collections.binarySearch(region.getSingles(), single);
            region.getSingles().add(Math.max(-j - 1, j), single);
        }
        return competitors.get(index);
    }

    @Override
    public BestEverRanksResponse generateAll() {
        return null;
    }
}
