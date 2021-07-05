package org.worldcubeassociation.statistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.model.Event;
import org.worldcubeassociation.statistics.repository.EventRepository;
import org.worldcubeassociation.statistics.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEvents(List<String> eventIds) {
        List<Event> events = eventRepository.findAllById(eventIds);
        if (events.size() != eventIds.size()) {
            List<String> existingEvents = events.stream().map(Event::getId).collect(Collectors.toList());
            List<String> invalidEvents = eventIds.stream().filter(eventId -> !existingEvents.contains(eventId)).collect(Collectors.toList());
            throw new InvalidParameterException("The following events are invalid: " + invalidEvents.stream().collect(Collectors.joining(", ")));
        }
        return events;
    }
}
