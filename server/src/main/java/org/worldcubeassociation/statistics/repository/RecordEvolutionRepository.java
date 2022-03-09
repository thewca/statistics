package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.worldcubeassociation.statistics.model.RecordEvolution;
import org.worldcubeassociation.statistics.repository.jdbc.RecordEvolutionRepositoryJdbc;

public interface RecordEvolutionRepository extends JpaRepository<RecordEvolution, String>, RecordEvolutionRepositoryJdbc {
}
