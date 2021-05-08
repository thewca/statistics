package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.model.BestEverRankPK;

import java.util.List;

@Repository
public interface BestEverRanksRepository extends JpaRepository<BestEverRank, BestEverRankPK> {
    List<BestEverRank> findByPersonId(String personId);
}
