package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.EventDto;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.repository.EventRepository;
import org.worldcubeassociation.statistics.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public EventServiceImpl(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll().stream().map(it -> objectMapper.convertValue(it, EventDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> getEvents(List<String> eventIds) {
        List<EventDto> events = eventRepository.findAllById(eventIds).stream().map(it -> objectMapper.convertValue(it, EventDto.class)).collect(Collectors.toList());
        if (events.size() != eventIds.size()) {
            List<String> existingEvents = events.stream().map(EventDto::getId).collect(Collectors.toList());
            List<String> invalidEvents = eventIds.stream().filter(eventId -> !existingEvents.contains(eventId)).collect(Collectors.toList());
            throw new InvalidParameterException("The following events are invalid: " + invalidEvents.stream().collect(Collectors.joining(", ")));
        }
        return events;
    }
}
