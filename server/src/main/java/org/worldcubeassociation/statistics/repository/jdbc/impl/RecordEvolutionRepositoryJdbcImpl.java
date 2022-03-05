package org.worldcubeassociation.statistics.repository.jdbc.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.dto.besteverrank.RegionDTO;
import org.worldcubeassociation.statistics.model.RecordEvolution;
import org.worldcubeassociation.statistics.repository.jdbc.RecordEvolutionRepositoryJdbc;
import org.worldcubeassociation.statistics.util.JsonUtil;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecordEvolutionRepositoryJdbcImpl implements RecordEvolutionRepositoryJdbc {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final List<Integer> BEST = List.of(0, 9, 99);

    public RecordEvolutionRepositoryJdbcImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    @Transactional
    public void upsert(RegionDTO region, LocalDate today) {
        MapSqlParameterSource paramsList = new MapSqlParameterSource().addValue(RecordEvolution.Fields.REGION.name(), region.getName()).addValue(RecordEvolution.Fields.EVOLUTION.name(), JsonUtil.convertToJson(workData(region, today)));

        namedJdbcTemplate.update(StatisticsUtil.getQuery("recordevolution/upsert"), paramsList);
    }

    @Override
    public int removeAll() {
        return namedJdbcTemplate.update(StatisticsUtil.getQuery("recordevolution/removeAll"), new HashMap<>());

    }

    private Map<String, Object> workData(RegionDTO region, LocalDate today) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", today.toString());
        for (int index : BEST) {
            maybeAddValue(index, region.getSingles(), result);
        }
        return result;
    }

    private void maybeAddValue(int index, List<Integer> results, Map<String, Object> result) {
        if (results.size() > index) {
            result.put("best" + (index + 1), results.get(index));
        }
    }
}
