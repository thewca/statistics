package org.worldcubeassociation.statistics.repository.jdbc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.map.property.GetterFactoryProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.dto.EventDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRankEventDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksRegionGroupDto;
import org.worldcubeassociation.statistics.repository.jdbc.SumOfRanksRepository;
import org.worldcubeassociation.statistics.util.LoadResourceUtil;

@Slf4j
@Repository
@AllArgsConstructor
public class SumOfRanksRepositoryImpl implements SumOfRanksRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final ObjectMapper objectMapper;

    private int baseRank(String singleQuery, String avgQuery) {
        int single = jdbcTemplate.update(
            LoadResourceUtil.getResource(String.format("db/query/sumofranks/%s.sql", singleQuery)));
        int avg = jdbcTemplate.update(
            LoadResourceUtil.getResource(String.format("db/query/sumofranks/%s.sql", avgQuery)));
        return single + avg;
    }

    @Override
    public int generateWorldRank() {
        log.info("Generate world rank");
        return baseRank("worldSingleInsert", "worldAverageInsert");
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("delete from sum_of_ranks where 1=1");
    }

    @Override
    public int generateContinentRank() {
        log.info("Generate continent rank");
        return baseRank("continentSingleInsert", "continentAverageInsert");
    }

    @Override
    public int generateCountryRank() {
        log.info("Generate country rank");
        return baseRank("countrySingleInsert", "countryAverageInsert");
    }

    @Override
    public List<SumOfRanksDto> list(String resultType, String regionType, String region, int page,
        int pageSize) {
        return namedJdbcTemplate.query(LoadResourceUtil.getResource("db/query/sumofranks/getSumOfRanks.sql"),
            new MapSqlParameterSource().addValue("REGION_TYPE", regionType)
                .addValue("REGION", region)
                .addValue("RESULT_TYPE", resultType).addValue("PAGE_SIZE", pageSize)
                .addValue("OFFSET", page * pageSize), JdbcTemplateMapperFactory.newInstance()
                .addColumnProperty("events",
                    GetterFactoryProperty.forType(List.class, (rs, i) -> Arrays.asList(
                        objectMapper.readValue(((ResultSet) rs).getString(i),
                            SumOfRankEventDto[].class))))
                .newRowMapper(SumOfRanksDto.class));
    }

    @Override
    public List<SumOfRanksMetaDto> getResultTypes() {
        return jdbcTemplate
            .query(LoadResourceUtil.getResource("db/query/sumofranks/getMeta.sql"),
                JdbcTemplateMapperFactory.newInstance()
                    .addColumnProperty("regionGroups",
                        GetterFactoryProperty.forType(List.class, (rs, i) -> Arrays.asList(
                            objectMapper.readValue(((ResultSet) rs).getString(i),
                                SumOfRanksRegionGroupDto[].class))))
                    .addColumnProperty("availableEvents",
                        GetterFactoryProperty.forType(List.class, (rs, i) -> Arrays.asList(
                            objectMapper.readValue(((ResultSet) rs).getString(i),
                                EventDto[].class))))
                    .newRowMapper(SumOfRanksMetaDto.class));
    }

    @Override
    public int deleteAllMeta() {
        return jdbcTemplate.update("delete from sum_of_ranks_meta where 1=1");
    }

    @Override
    public int insertMeta() {
        return jdbcTemplate.update(LoadResourceUtil.getResource("db/query/sumofranks/insertMeta.sql"));
    }

    @Override
    public void updateRanks() {
        jdbcTemplate.update(LoadResourceUtil.getResource("db/query/sumofranks/updateRank.sql"));
    }

    @Override
    public Optional<Integer> getWcaIdPage(String resultType, String regionType, String region,
        int pageSize,
        String wcaId) {
        List<Integer> list = namedJdbcTemplate.queryForList(
            LoadResourceUtil.getResource("db/query/sumofranks/getWcaIdPage.sql"),
            new MapSqlParameterSource()
                .addValue("RESULT_TYPE", resultType)
                .addValue("REGION_TYPE", regionType)
                .addValue("REGION", region)
                .addValue("PAGE_SIZE", pageSize)
                .addValue("WCA_ID", wcaId), Integer.class);

        return Optional.ofNullable(list.size() != 1 ? null : list.get(0));
    }
}
