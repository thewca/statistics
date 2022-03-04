package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.*;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.model.Event;
import org.worldcubeassociation.statistics.repository.BestEverRanksRepository;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksEventResponse;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;
import org.worldcubeassociation.statistics.service.BestEverRanksService;
import org.worldcubeassociation.statistics.service.EventService;
import org.worldcubeassociation.statistics.service.RecordEvolutionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BestEverRanksServiceImpl implements BestEverRanksService {
    @Autowired
    private BestEverRanksRepository bestEverRanksRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecordEvolutionService recordEvolutionService;

    @Override
    public BestEverRankDTO get(String personId) {
        BestEverRank bestEverRank = bestEverRanksRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Rank not found for " + personId));
        return objectMapper.convertValue(bestEverRank, BestEverRankDTO.class);
    }

    @Override
    public BestEverRanksResponse generate(BestEverRanksRequest bestEverRanksRequest) {
        List<Event> events = eventService.getEvents(bestEverRanksRequest.getEventIds());
        return generateByEventList(events);
    }

    private BestEverRanksResponse generateByEventList(List<Event> events) {
        log.info("{} events to generate", events.size());

        log.info("Delete all existing ranks");
        int deleted = bestEverRanksRepository.removeAll();
        log.info("{} results deleted", deleted);

        recordEvolutionService.removeAll();

        BestEverRanksResponse bestEverRanksResponse = new BestEverRanksResponse();
        bestEverRanksResponse.setEvents(new ArrayList<>());
        for (Event event : events) {
            LocalDateTime start = LocalDateTime.now();

            log.info("Generate ranks for {}", event.getId());
            generateByEventId(event, bestEverRanksResponse);

            LocalDateTime end = LocalDateTime.now();

            log.info("Generated in {} minutes", start.until(end, ChronoUnit.MINUTES));
        }

        return bestEverRanksResponse;
    }

    private void generateByEventId(Event event, BestEverRanksResponse bestEverRanksResponse) {
        String eventId = event.getId();

        log.info("Current event: {}", eventId);

        List<LocalDate> dates = bestEverRanksRepository.getDates(eventId);
        log.info("Found {} dates", dates.size());

        List<RegionDTO> worlds = new ArrayList<>();
        List<RegionDTO> continents = new ArrayList<>();
        List<RegionDTO> countries = new ArrayList<>();

        worlds.add(new RegionDTO("world"));

        int currentYear = -1;
        for (LocalDate date : dates) {

            // Just to get a track of how long it's been
            if (date.getYear() != currentYear) {
                currentYear = date.getYear();
                log.info("Current year: {}", currentYear);
            }
            List<CompetitorCountryDTO> todayCompetitors = bestEverRanksRepository.getTodayCompetitors(date, eventId);

            if (todayCompetitors.isEmpty()) {
                continue;
            }

            summarizeResults(todayCompetitors, worlds, continents, countries, date);
        }

        saveResults(event, worlds, continents, countries);

        BestEverRanksEventResponse bestEverRanksEventResponse = new BestEverRanksEventResponse();
        bestEverRanksEventResponse.setEventId(event.getId());
        bestEverRanksEventResponse.setUpdatedResults(worlds.get(0).getCompetitors().size());
        bestEverRanksResponse.getEvents().add(bestEverRanksEventResponse);
    }

    private void saveResults(Event event, List<RegionDTO> worlds, List<RegionDTO> continents, List<RegionDTO> countries) {
        List<BestEverRank> bestEverRanks = new ArrayList<>();

        EventDTO eventDTO = objectMapper.convertValue(event, EventDTO.class);

        for (Competitor competitor : worlds.get(0).getCompetitors()) {
            BestEverRank bestEverRank = new BestEverRank();
            bestEverRank.setPersonId(competitor.getWcaId());

            List<EventRankDTO> eventRanks = new ArrayList<>();

            EventRankDTO eventRank = new EventRankDTO(eventDTO);
            eventRank.getWorlds().add((CompetitorWorldDTO) competitor);
            eventRanks.add(eventRank);

            bestEverRank.setEventRanks(eventRanks);
            bestEverRanks.add(bestEverRank);
        }

        for (RegionDTO continent : continents) {
            for (Competitor competitor : continent.getCompetitors()) {

                // It's guaranteed that there exist the current event rank already
                BestEverRank bestEverRank = new BestEverRank();
                bestEverRank.setPersonId(competitor.getWcaId());
                int index = Collections.binarySearch(bestEverRanks, bestEverRank);
                bestEverRank = bestEverRanks.get(index);

                List<EventRankDTO> eventRanks = bestEverRank.getEventRanks();

                EventRankDTO eventRank = eventRanks.get(0);
                eventRank.getContinents().add((CompetitorContinentDTO) competitor);
            }
        }

        for (RegionDTO country : countries) {
            for (Competitor competitor : country.getCompetitors()) {

                BestEverRank bestEverRank = new BestEverRank();
                bestEverRank.setPersonId(competitor.getWcaId());
                int index = Collections.binarySearch(bestEverRanks, bestEverRank);
                bestEverRank = bestEverRanks.get(index);

                List<EventRankDTO> eventRanks = bestEverRank.getEventRanks();

                EventRankDTO eventRank = eventRanks.get(0);
                eventRank.getCountries().add((CompetitorCountryDTO) competitor);
            }
        }

        Integer totalUpdated = bestEverRanksRepository.upsert(bestEverRanks, event.getId());
        log.info("Updated {} results for {}", totalUpdated, event.getId());
    }

    private void summarizeResults(List<CompetitorCountryDTO> todayCompetitors, List<RegionDTO> worlds, List<RegionDTO> continents, List<RegionDTO> countries, LocalDate today) {
        RegionDTO world = worlds.get(0);

        // Assign today's best result
        for (CompetitorCountryDTO todayCompetitor : todayCompetitors) {
            CompetitorWorldDTO competitorWorld = new CompetitorWorldDTO(todayCompetitor);
            updateResults(world, competitorWorld);

            RegionDTO continent = findOrCreateRegion(continents, todayCompetitor.getContinent());
            CompetitorContinentDTO competitorContinentDTO = new CompetitorContinentDTO(todayCompetitor);
            updateResults(continent, competitorContinentDTO);

            RegionDTO country = findOrCreateRegion(countries, todayCompetitor.getCountry());
            updateResults(country, todayCompetitor);
        }

        findRanks(world, today);
        for (RegionDTO continent : continents) {
            findRanks(continent, today);
        }
        for (RegionDTO country : countries) {
            findRanks(country, today);
        }
    }

    private void findRanks(RegionDTO region, LocalDate today) {
        for (Competitor competitor : region.getCompetitors()) {
            analyzeRank(region.getSingles(), competitor.getSingle(), today);

            if (competitor.getAverage().getCurrent().getResult() != null) {
                analyzeRank(region.getAverages(), competitor.getAverage(), today);
            }
        }
        recordEvolutionService.registerEvolution(region, today);
    }

    private void analyzeRank(List<Integer> regionResults, ResultsDTO competitorResults, LocalDate today) {
        int currentRank = binarySearchLeft(regionResults, competitorResults.getCurrent().getResult());
        competitorResults.getCurrent().setRank(currentRank);

        Integer oldRank = competitorResults.getBestRank().getRank();
        if (oldRank == null || currentRank < oldRank) {
            competitorResults.getBestRank().setResult(competitorResults.getCurrent().getResult());
            competitorResults.getBestRank().setCompetition(competitorResults.getCurrent().getCompetition());
            competitorResults.getBestRank().setStart(competitorResults.getCurrent().getStart());
            competitorResults.getBestRank().setRank(competitorResults.getCurrent().getRank());
            competitorResults.getBestRank().setEnd(null);
        } else if (currentRank > oldRank && competitorResults.getBestRank().getEnd() == null) {
            competitorResults.getBestRank().setEnd(today.minusDays(1));
        }
    }

    private int binarySearchLeft(List<Integer> list, Integer result) {
        int low = 0;
        int hi = list.size();
        while (low < hi) {
            int mid = (low + hi) / 2;
            if (list.get(mid) >= result) {
                hi = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private RegionDTO findOrCreateRegion(List<RegionDTO> regions, String name) {
        RegionDTO region = new RegionDTO(name);

        int index = Collections.binarySearch(regions, region);
        if (index < 0) {
            index = -index - 1;
            regions.add(index, region);
        }

        return regions.get(index);
    }

    private void updateResults(RegionDTO region, Competitor todayCompetitor) {
        Competitor regionCompetitor = findOrCreateCompetitor(region, todayCompetitor);

        updateResult(region.getSingles(), regionCompetitor.getSingle(), todayCompetitor.getSingle());
        updateResult(region.getAverages(), regionCompetitor.getAverage(), todayCompetitor.getAverage());
    }

    private void updateResult(List<Integer> results, ResultsDTO oldResult, ResultsDTO newResult) {
        Integer oldBest = oldResult.getCurrent().getResult();
        Integer newBest = newResult.getCurrent().getResult();

        if (newBest != null && (oldBest == null || newBest < oldBest)) {
            // We remove the old result and insert the new one

            if (oldBest != null) {
                int index = Collections.binarySearch(results, oldBest);
                results.remove(index);
            }

            int j = Collections.binarySearch(results, newBest);
            results.add(Math.max(-j - 1, j), newBest);

            oldResult.setCurrent(newResult.getCurrent());
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
            maybeAddResult(region.getSingles(), competitor.getSingle().getCurrent().getResult());
            maybeAddResult(region.getAverages(), competitor.getAverage().getCurrent().getResult());
        }
        return competitors.get(index);
    }

    private void maybeAddResult(List<Integer> results, Integer result) {
        if (result != null) {
            int j = Collections.binarySearch(results, result);
            results.add(Math.max(-j - 1, j), result);
        }

    }

    @Override
    public BestEverRanksResponse generateAll() {
        List<Event> events = eventService.findAll();
        return generateByEventList(events);
    }
}
