package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorCountryDTO;

import java.time.LocalDate;
import java.util.List;

public interface BestEverRanksRepositoryJdbc {
    List<LocalDate> getDates(String eventId);

    List<CompetitorCountryDTO> getTodayCompetitors(LocalDate date, String eventId);
}
