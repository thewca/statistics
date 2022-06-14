package org.worldcubeassociation.statistics.repository.jdbc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.map.property.GetterFactoryProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRankEventDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksRegionGroupDto;
import org.worldcubeassociation.statistics.repository.jdbc.SumOfRanksRepository;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class SumOfRanksRepositoryImpl implements SumOfRanksRepository {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void generateWorldRank() {
        log.info("Generate world rank");
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/worldSingleInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/worldSingleUpdate"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/worldAverageInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/worldAverageUpdate"));
    }

    @Override
    public void deleteAll() {
        namedJdbcTemplate.getJdbcTemplate().update("delete from sum_of_ranks where 1=1");
    }

    @Override
    public void generateContinentRank() {
        log.info("Generate continent rank");
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/continentSingleInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/continentSingleUpdate"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/continentAverageInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/continentAverageUpdate"));
    }

    @Override
    public void generateCountryRank() {
        log.info("Generate country rank");
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/countrySingleInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/countrySingleUpdate"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/countryAverageInsert"));
        namedJdbcTemplate.getJdbcTemplate().update(StatisticsUtil.getQuery("sumofranks/countryAverageUpdate"));
    }

    @Override
    public List<SumOfRanksDto> list(String regionType, String region, String resultType, int page,
                                    int pageSize) {
        return namedJdbcTemplate.query(StatisticsUtil.getQuery("sumofranks/getSumOfRanks"),
                new MapSqlParameterSource().addValue("REGION_TYPE", regionType).addValue("REGION", region)
                        .addValue("RESULT_TYPE", resultType).addValue("PAGE_SIZE", pageSize)
                        .addValue("OFFSET", page * pageSize), JdbcTemplateMapperFactory.newInstance()
                        .addColumnProperty("events", GetterFactoryProperty.forType(List.class, (rs, i) -> Arrays.asList(
                                objectMapper.readValue(((ResultSet) rs).getString(i), SumOfRankEventDto[].class))))
                        .newRowMapper(SumOfRanksDto.class));
    }

    @Override
    public List<SumOfRanksRegionGroupDto> getRegions() {
        return namedJdbcTemplate.getJdbcTemplate()
                .query(StatisticsUtil.getQuery("sumofranks/getRegions"), JdbcTemplateMapperFactory.newInstance()
                        .newRowMapper(SumOfRanksRegionGroupDto.class));
    }
}
