package org.worldcubeassociation.statistics.repository.jdbc.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.model.RecordEvolution;
import org.worldcubeassociation.statistics.repository.jdbc.RecordEvolutionRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.time.LocalDate;

import static org.worldcubeassociation.statistics.util.JsonUtil.convertToJson;

@Repository
public class RecordEvolutionRepositoryJdbcImpl implements RecordEvolutionRepositoryJdbc {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public RecordEvolutionRepositoryJdbcImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    @Transactional
    public void upsert(RegionDTO region, LocalDate today) {
        MapSqlParameterSource paramsList = new MapSqlParameterSource()
                .addValue(RecordEvolution.Fields.REGION.name(), region.getName())
                .addValue(RecordEvolution.Fields.EVOLUTION.name(), convertToJson(region.getCompetitors()));

        namedJdbcTemplate.update(StatisticsUtil.getQuery("recordevolution/upsert"), paramsList);

    }
}
