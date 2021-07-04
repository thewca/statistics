package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.*;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.model.Event;
import org.worldcubeassociation.statistics.repository.BestEverRanksRepository;
import org.worldcubeassociation.statistics.repository.EventRepository;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BestEverRanksServiceImpl implements BestEverRanksService {
    @Autowired
    private BestEverRanksRepository bestEverRanksRepository;

    @Autowired
    private EventRepository eventRepository;

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

        List<Event> events = getEvents(bestEverRanksRequest.getEventIds());

        log.info("Delete all existing ranks");
        int deleted = bestEverRanksRepository.removeAll();
        log.info("{} results deleted", deleted);

        BestEverRanksResponse bestEverRanksResponse = new BestEverRanksResponse();
        for (Event event : events) {
            log.info("Generate ranks for {}", event.getId());
            generateByEventId(event, bestEverRanksResponse);
        }

        return bestEverRanksResponse;
    }

    private List<Event> getEvents(List<String> eventIds) {
        List<Event> events = eventRepository.findAllById(eventIds);
        if (events.size() != eventIds.size()) {
            List<String> existingEvents = events.stream().map(Event::getId).collect(Collectors.toList());
            List<String> invalidEvents = eventIds.stream().filter(eventId -> !existingEvents.contains(eventId)).collect(Collectors.toList());
            throw new InvalidParameterException("The following events are invalid: " + invalidEvents.stream().collect(Collectors.joining(", ")));
        }
        return events;
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

            summarizeResults(todayCompetitors, worlds, continents, countries, date);
        }

        saveResults(event, worlds, continents, countries);
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
            updateResults(world, competitorWorld, today);

            RegionDTO continent = findOrCreateRegion(continents, todayCompetitor.getContinent());
            CompetitorContinentDTO competitorContinentDTO = new CompetitorContinentDTO(todayCompetitor);
            updateResults(continent, competitorContinentDTO, today);

            RegionDTO country = findOrCreateRegion(countries, todayCompetitor.getCountry());
            updateResults(country, todayCompetitor, today);
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
    }

    private void analyzeRank(List<Integer> regionResults, ResultsDTO competitorResults, LocalDate today) {
        int currentRank = Collections.binarySearch(regionResults, competitorResults.getCurrent().getResult());
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

    private RegionDTO findOrCreateRegion(List<RegionDTO> regions, String name) {
        RegionDTO region = new RegionDTO(name);

        int index = Collections.binarySearch(regions, region);
        if (index < 0) {
            index = -index - 1;
            regions.add(index, region);
        }

        return regions.get(index);
    }

    private void updateResults(RegionDTO region, Competitor todayCompetitor, LocalDate today) {
        Competitor regionCompetitor = findOrCreateCompetitor(region, todayCompetitor);

        // Singles are always non null
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
