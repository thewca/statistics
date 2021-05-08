package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;

import java.util.List;

@FunctionalInterface
@RequestMapping("best-ever-rank")
public interface BestEverRanksController {
    @PostMapping("{personId}")
    List<BestEverRankDTO> get(@PathVariable String personId);
}
