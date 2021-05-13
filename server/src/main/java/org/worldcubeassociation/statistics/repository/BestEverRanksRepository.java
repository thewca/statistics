package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.model.BestEverRank;

@Repository
public interface BestEverRanksRepository extends JpaRepository<BestEverRank, String> {
}
