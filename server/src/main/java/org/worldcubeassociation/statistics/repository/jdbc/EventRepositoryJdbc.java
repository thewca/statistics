package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.model.Event;

import java.util.List;

public interface EventRepositoryJdbc {
    List<Event> findAllById(List<String> ids);

    List<Event> findAll();

}
