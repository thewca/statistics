package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.model.StatisticsControl;

@Repository
public interface StatisticsControlRepository extends JpaRepository<StatisticsControl, String> {

}
