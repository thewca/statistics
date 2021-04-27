package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.worldcubeassociation.statistics.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {
}
