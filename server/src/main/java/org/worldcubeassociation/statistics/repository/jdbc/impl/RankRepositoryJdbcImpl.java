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
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/worldSingleInsert"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/worldSingleUpdate"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/worldAverageInsert"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/worldAverageUpdate"));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from sum_of_ranks where 1=1");
    }

    @Override
    public void generateContinentRank() {
        log.info("Generate continent rank");
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/continentSingleInsert"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/continentSingleUpdate"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/continentAverageInsert"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/continentAverageUpdate"));
    }

    @Override
    public void generateCountryRank() {
        log.info("Generate country rank");
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/countrySingleInsert"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/countrySingleUpdate"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/countryAverageInsert"));
        jdbcTemplate.update(StatisticsUtil.getQuery("rank/countryAverageUpdate"));
    }
}
