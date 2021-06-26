package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.repository.jdbc.BestEverRanksRepositoryJdbc;

@Repository
public interface BestEverRanksRepository extends JpaRepository<BestEverRank, String>, BestEverRanksRepositoryJdbc {
}
