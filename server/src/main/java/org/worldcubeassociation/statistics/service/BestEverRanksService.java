package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;

public interface BestEverRanksService {
    BestEverRankDTO get(String personId);

    BestEverRanksResponse generate(BestEverRanksRequest bestEverRanksRequest);

    BestEverRanksResponse generateAll();
}
