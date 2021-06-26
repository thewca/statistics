package org.worldcubeassociation.statistics.repository.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.worldcubeassociation.statistics.repository.jdbc.BestEverRanksRepositoryJdbc;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BestEverRanksRepositoryJdbcImpl implements BestEverRanksRepositoryJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<LocalDate> getDates(BestEverRanksRequest bestEverRanksRequest) {
        return new ArrayList<>();
    }
}
