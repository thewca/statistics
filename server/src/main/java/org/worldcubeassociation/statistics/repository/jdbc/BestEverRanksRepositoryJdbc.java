package org.worldcubeassociation.statistics.repository.jdbc;

import org.worldcubeassociation.statistics.request.BestEverRanksRequest;

import java.time.LocalDate;
import java.util.List;

public interface BestEverRanksRepositoryJdbc {
    List<LocalDate> getDates(BestEverRanksRequest bestEverRanksRequest);
}
