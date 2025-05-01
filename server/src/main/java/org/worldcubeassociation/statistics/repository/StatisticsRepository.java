package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.model.Statistics;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {

    @Query("select new org.worldcubeassociation.statistics.dto.ControlItemDTO(path, title, groupName) from Statistics"
            + " where :term is null or :term = '' or lower(cast(statistics as char)) like concat('%', lower(:term), '%')")
    List<ControlItemDTO> list(String term);

    // Actually, it is just an approximation
    @Query(value = "select max(results_posted_at) from competitions", nativeQuery = true)
    LocalDateTime getExportDate();
}
