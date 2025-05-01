package org.worldcubeassociation.statistics.repository.jdbc.impl;

import java.util.List;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.worldcubeassociation.statistics.model.Event;
import org.worldcubeassociation.statistics.repository.jdbc.EventRepositoryJdbc;
import org.worldcubeassociation.statistics.util.LoadResourceUtil;

public class EventRepositoryJdbcImpl implements EventRepositoryJdbc {

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<Event> findAllById(List<String> ids) {
        return namedJdbcTemplate
            .query(
                LoadResourceUtil.getResource("db/query/event/findAllById.sql"),
                new MapSqlParameterSource().addValue(Event.Fields.ID.name(), ids),
                JdbcTemplateMapperFactory
                    .newInstance()
                    .newRowMapper(Event.class)
            );
    }

    @Override
    public List<Event> findAll() {
        return namedJdbcTemplate
            .query(
                LoadResourceUtil.getResource("db/query/event/findAll.sql"),
                new MapSqlParameterSource(),
                JdbcTemplateMapperFactory
                    .newInstance()
                    .newRowMapper(Event.class)
            );
    }
}
