package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("rank")
public interface RankController {
    @PostMapping("sum-of-ranks")
    void generateSumOfRanks();
}
