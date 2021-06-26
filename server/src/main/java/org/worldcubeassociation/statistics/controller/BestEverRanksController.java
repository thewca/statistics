package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.*;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;

@RequestMapping("best-ever-rank")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface BestEverRanksController {
    @GetMapping("{personId}")
    BestEverRankDTO get(@PathVariable String personId);

    @PostMapping("{eventId}")
    BestEverRanksResponse generate(String eventId);

    @PostMapping
    BestEverRanksResponse generate();

}
