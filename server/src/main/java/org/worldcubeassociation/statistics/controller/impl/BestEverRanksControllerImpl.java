package org.worldcubeassociation.statistics.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.BestEverRanksController;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

import java.util.List;

@RestController
public class BestEverRanksControllerImpl implements BestEverRanksController {
    @Autowired
    private BestEverRanksService bestEverRanksService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<BestEverRankDTO> get(String personId) {
        return bestEverRanksService.get(personId);
    }
}
