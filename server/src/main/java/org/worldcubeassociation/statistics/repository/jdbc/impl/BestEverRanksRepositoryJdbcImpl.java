package org.worldcubeassociation.statistics.repository.jdbc.impl;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorContinentDTO;
import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorCountryDTO;
import org.worldcubeassociation.statistics.dto.besteverrank.CompetitorWorldDTO;
import org.worldcubeassociation.statistics.dto.besteverrank.ResultsDTO;
import org.worldcubeassociation.statistics.repository.jdbc.BestEverRanksRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BestEverRanksRepositoryJdbcImpl implements BestEverRanksRepositoryJdbc {
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

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
                            competitor.setAverage(new ResultsDTO(average, competition, date));

                            return competitor;
                        }
                );
    }
}
