package org.worldcubeassociation.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.worldcubeassociation.statistics.dto.ControlItemDTO;
import org.worldcubeassociation.statistics.model.Statistics;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, String> {

    @Query(value = "select new org.worldcubeassociation.statistics.dto.ControlItemDTO(path, title, groupName) from "
            + "Statistics")
    List<ControlItemDTO> list();
}
