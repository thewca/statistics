package org.worldcubeassociation.statistics.repository.jdbc.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.repository.jdbc.RankRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

@Repository
@AllArgsConstructor
public class RankRepositoryJdbcImpl implements RankRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void generateWorldRank() {
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/generateWorldRankSingle"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/updateWorldRankSingle"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/generateWorldRankAverage"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/updateWorldRankAverage"));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from sum_of_ranks where 1=1");
    }
}
