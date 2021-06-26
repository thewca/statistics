package org.worldcubeassociation.statistics.repository.jdbc.impl;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.repository.jdbc.BestEverRanksRepositoryJdbc;
import org.worldcubeassociation.statistics.util.StatisticsUtil;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BestEverRanksRepositoryJdbcImpl implements BestEverRanksRepositoryJdbc {
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String EVENT_ID = "EVENT_ID";

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
}
