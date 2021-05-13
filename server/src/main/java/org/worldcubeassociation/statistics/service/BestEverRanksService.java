package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;

public interface BestEverRanksService {
    BestEverRankDTO get(String personId);
}
