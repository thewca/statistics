package org.worldcubeassociation.statistics.repository.jdbc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.map.property.GetterFactoryProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.dto.rank.SumOfRankEventDto;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;
import org.worldcubeassociation.statistics.repository.jdbc.RankRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class RankRepositoryJdbcImpl implements RankRepositoryJdbc {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void generateWorldRank() {
        log.info("Generate world rank");
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/worldSingleInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/worldSingleUpdate"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/worldAverageInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/worldAverageUpdate"));
    }

    @Override
    public void deleteAll() {
        namedJdbcTemplate.getJdbcTemplate().update("delete from sum_of_ranks where 1=1");
    }

    @Override
    public void generateContinentRank() {
        log.info("Generate continent rank");
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/continentSingleInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/continentSingleUpdate"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/continentAverageInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/continentAverageUpdate"));
    }

    @Override
    public void generateCountryRank() {
        log.info("Generate country rank");
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/countrySingleInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/countrySingleUpdate"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/countryAverageInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("rank/countryAverageUpdate"));
    }

    @Override
    public List<SumOfRanksDto> getSumOfRanks(String regionType, String region, String resultType, int page,
                                             int pageSize) {
        return namedJdbcTemplate.query(StatisticsUtil.getQuery("rank/getSumOfRanks"),
                new MapSqlParameterSource().addValue("REGION_TYPE", regionType).addValue("REGION", region)
                        .addValue("RESULT_TYPE", resultType).addValue("PAGE_SIZE", pageSize)
                        .addValue("OFFSET", page * pageSize), JdbcTemplateMapperFactory.newInstance()
                        .addColumnProperty("events", GetterFactoryProperty.forType(List.class, (rs, i) -> Arrays.asList(
                                objectMapper.readValue(((ResultSet) rs).getString(i), SumOfRankEventDto[].class))))
                        .newRowMapper(SumOfRanksDto.class));
    }
}
