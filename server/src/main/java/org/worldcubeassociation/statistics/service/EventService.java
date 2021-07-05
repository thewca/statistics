package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.model.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();

    List<Event> getEvents(List<String> eventIds);
}
