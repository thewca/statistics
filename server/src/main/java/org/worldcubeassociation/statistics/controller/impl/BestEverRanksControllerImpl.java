package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.BestEverRanksController;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;
import org.worldcubeassociation.statistics.service.AuthorizationService;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

@RestController
public class BestEverRanksControllerImpl implements BestEverRanksController {
    @Autowired
    private BestEverRanksService bestEverRanksService;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public BestEverRankDTO get(String personId) {
        return bestEverRanksService.get(personId);
    }

    @Override
    public BestEverRanksResponse generate(BestEverRanksRequest bestEverRanksRequest) {
        authorizationService.disableInProd();
        return bestEverRanksService.generate(bestEverRanksRequest);
    }

    @Override
    public BestEverRanksResponse generateAll() {
        authorizationService.disableInProd();
        return bestEverRanksService.generateAll();
    }
}
