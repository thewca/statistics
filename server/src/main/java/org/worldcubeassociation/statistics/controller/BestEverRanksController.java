package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.*;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;
import org.worldcubeassociation.statistics.response.BestEverRanksResponse;

import jakarta.validation.Valid;

@RequestMapping("best-ever-rank")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface BestEverRanksController {
    @GetMapping("{personId}")
    BestEverRankDTO get(@PathVariable String personId);

    @PostMapping("generate")
    BestEverRanksResponse generate(@RequestBody @Valid BestEverRanksRequest bestEverRanksRequest);

    @PostMapping("generate/all")
    BestEverRanksResponse generateAll();
}
