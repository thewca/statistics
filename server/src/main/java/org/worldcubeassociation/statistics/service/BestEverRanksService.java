package org.worldcubeassociation.statistics.service;

import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;

import java.util.List;

public interface BestEverRanksService {
    List<BestEverRankDTO> get(String personId);
}
