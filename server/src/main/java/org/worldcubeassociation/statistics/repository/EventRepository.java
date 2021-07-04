package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.model.Event;
import org.worldcubeassociation.statistics.repository.jdbc.EventRepositoryJdbc;

@Repository
public interface EventRepository extends JpaRepository<Event, String>, EventRepositoryJdbc {
}
