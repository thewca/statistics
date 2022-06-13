package org.worldcubeassociation.statistics.repository.jdbc.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.repository.jdbc.RankRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

@Slf4j
@Repository
@AllArgsConstructor
public class RankRepositoryJdbcImpl implements RankRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void generateWorldRank() {
        log.info("Generate world rank");
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/generateWorldRankSingle"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/updateWorldRankSingle"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/generateWorldRankAverage"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/updateWorldRankAverage"));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from sum_of_ranks where 1=1");
    }

    @Override
    public void generateContinentRank() {
        log.info("Generate continent rank");
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/generateContinentRankSingle"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/updateContinentRankSingle"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/generateContinentRankAverage"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/updateContinentRankAverage"));
    }

    @Override
    public void generateCountryRank() {

    }
}
