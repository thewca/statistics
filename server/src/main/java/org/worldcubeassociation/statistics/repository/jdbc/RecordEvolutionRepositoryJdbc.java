package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.dto.EventDto;
import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;

import java.time.LocalDate;
import java.util.List;

public interface RecordEvolutionRepositoryJdbc {
    void upsert(RegionDTO region, String eventId, LocalDate today);

    int removeAll();

    List<EventDto> getAvailableEvents();
}
