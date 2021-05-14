package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.BestEverRanksController;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

@RestController
public class BestEverRanksControllerImpl implements BestEverRanksController {
    @Autowired
    private BestEverRanksService bestEverRanksService;

    @Override
    public BestEverRankDTO get(String personId) {
        return bestEverRanksService.get(personId);
    }
}
