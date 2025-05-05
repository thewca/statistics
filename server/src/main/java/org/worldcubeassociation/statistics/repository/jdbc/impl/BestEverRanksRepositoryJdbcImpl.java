package org.worldcubeassociation.statistics.repository.jdbc.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorContinentDTO;
import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorCountryDTO;
import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorWorldDTO;
import org.worldcubeassociation.statistics.dto.besteverrank.ResultsDTO;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.repository.jdbc.BestEverRanksRepositoryJdbc;
import org.worldcubeassociation.statistics.util.JsonUtil;
import org.worldcubeassociation.statistics.util.LoadResourceUtil;

@Repository
public class BestEverRanksRepositoryJdbcImpl implements BestEverRanksRepositoryJdbc {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JsonUtil jsonUtil;

    private static final String EVENT_ID = "EVENT_ID";
    private static final String DATE = "DATE";
    private static final String COMPETITION = "COMPETITION";

    public BestEverRanksRepositoryJdbcImpl(NamedParameterJdbcTemplate namedJdbcTemplate,
        JsonUtil jsonUtil) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public List<LocalDate> getDates(String eventId) {
        return namedJdbcTemplate
            .query(
                LoadResourceUtil.getResource("db/query/besteverranks/getDates.sql"),
                new MapSqlParameterSource().addValue(EVENT_ID, eventId),
                JdbcTemplateMapperFactory
                    .newInstance()
                    .newRowMapper(LocalDate.class)
            );
    }

    @Override
    public List<CompetitorCountryDTO> getTodayCompetitors(LocalDate date, String eventId) {
        return namedJdbcTemplate
            .query(
                LoadResourceUtil.getResource("db/query/besteverranks/getTodayCompetitors.sql"),
                new MapSqlParameterSource().addValue(EVENT_ID, eventId).addValue(DATE, date),
                (rs, rowNum) -> {
                    String wcaId = rs.getString(CompetitorWorldDTO.Fields.WCA_ID.name());
                    String continent = rs.getString(CompetitorContinentDTO.Fields.CONTINENT.name());
                    String country = rs.getString(CompetitorCountryDTO.Fields.COUNTRY.name());
                    Integer single = rs.getInt(CompetitorWorldDTO.Fields.SINGLE.name());
                    Integer average = rs.getInt(CompetitorWorldDTO.Fields.AVERAGE.name());
                    String competition = rs.getString(COMPETITION);

                    CompetitorCountryDTO competitor = new CompetitorCountryDTO();
                    competitor.setWcaId(wcaId);
                    competitor.setContinent(continent);
                    competitor.setCountry(country);
                    competitor.setSingle(new ResultsDTO(single, competition, date));
                    competitor.setAverage(
                        new ResultsDTO(average == 0 ? null : average, competition, date));

                    return competitor;
                }
            );
    }

    @Override
    @Transactional
    public Integer upsert(List<BestEverRank> bestEverRanks, String eventId) {
        MapSqlParameterSource[] paramsList = bestEverRanks
            .stream()
            .map(ber -> new MapSqlParameterSource()
                .addValue(BestEverRank.Fields.PERSON_ID.name(), ber.getPersonId())
                .addValue(BestEverRank.Fields.EVENT_RANKS.name(),
                    jsonUtil.convertToJsonArray(ber.getEventRanks()))
            )
            .toArray(MapSqlParameterSource[]::new);

        return namedJdbcTemplate
            .batchUpdate(
                LoadResourceUtil.getResource("db/query/besteverranks/upsert.sql"),
                paramsList).length;

    }

    @Override
    public Integer removeAll() {
        return namedJdbcTemplate
            .update(LoadResourceUtil.getResource("db/query/besteverranks/removeAll.sql"),
                new HashMap<>());
    }
}
