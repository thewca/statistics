package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> findAll();

    List<EventDto> getEvents(List<String> eventIds);
}
