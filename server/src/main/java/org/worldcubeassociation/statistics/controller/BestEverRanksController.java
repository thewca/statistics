package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;

@RequestMapping("best-ever-rank")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Enable this for testing
public interface BestEverRanksController {
    @GetMapping("{personId}")
    BestEverRankDTO get(@PathVariable String personId);
}
