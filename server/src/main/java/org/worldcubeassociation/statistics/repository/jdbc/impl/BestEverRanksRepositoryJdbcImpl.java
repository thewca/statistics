package org.worldcubeassociation.statistics.repository.jdbc.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.dto.besteverrank.*;
import org.worldcubeassociation.statistics.model.BestEverRank;
import org.worldcubeassociation.statistics.repository.jdbc.BestEverRanksRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public class BestEverRanksRepositoryJdbcImpl implements BestEverRanksRepositoryJdbc {
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String EVENT_ID = "EVENT_ID";
    private static final String DATE = "DATE";

    @Override
    public List<LocalDate> getDates(String eventId) {
        return namedJdbcTemplate
                .query(
                        StatisticsUtil.getQuery("besteverranks/getDates"),
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
                        StatisticsUtil.getQuery("besteverranks/getTodayCompetitors"),
                        new MapSqlParameterSource().addValue(EVENT_ID, eventId).addValue(DATE, date),
                        (rs, rowNum) -> {
                            String wcaId = rs.getString(CompetitorWorldDTO.Fields.WCA_ID.name());
                            String continent = rs.getString(CompetitorContinentDTO.Fields.CONTINENT.name());
                            String country = rs.getString(CompetitorCountryDTO.Fields.COUNTRY.name());
                            Integer single = rs.getInt(CompetitorWorldDTO.Fields.SINGLE.name());
                            Integer average = rs.getInt(CompetitorWorldDTO.Fields.AVERAGE.name());
                            String competition = rs.getString(CompetitorWorldDTO.Fields.COMPETITION.name());

                            CompetitorCountryDTO competitor = new CompetitorCountryDTO();
                            competitor.setWcaId(wcaId);
                            competitor.setContinent(continent);
                            competitor.setCountry(country);
                            competitor.setSingle(new ResultsDTO(single, competition, date));
                            competitor.setAverage(new ResultsDTO(average == 0 ? null : average, competition, date));

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
                        .addValue(BestEverRank.Fields.EVENT_RANKS.name(), convertToJson(ber.getEventRanks()))
                )
                .toArray(MapSqlParameterSource[]::new);

        return namedJdbcTemplate
                .batchUpdate(
                        StatisticsUtil.getQuery("besteverranks/upsert"),
                        paramsList).length;

    }

    @Override
    public Integer removeAll() {
        return namedJdbcTemplate
                .update(StatisticsUtil.getQuery("besteverranks/removeAll"), new HashMap<>());
    }

    private Object convertToJson(List<EventRankDTO> eventRanks) {
        try {
            return objectMapper.writeValueAsString(eventRanks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }


}
