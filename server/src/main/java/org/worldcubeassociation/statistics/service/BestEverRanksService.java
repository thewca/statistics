package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;

public interface BestEverRanksService {
    BestEverRankDTO get(String personId);

    BestEverRanksResponse generate(String eventId);

    BestEverRanksResponse generate();
}
