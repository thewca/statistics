package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;

import java.util.List;

@RequestMapping("rank")
public interface RankController {
    @PostMapping("sum-of-ranks")
    void generateSumOfRanks();

    @GetMapping("sum-of-ranks/{regionType}/{region}/{resultType}")
    List<SumOfRanksDto> getSumOfRanks(String regionType, String region, String resultType, int page, int pageSize);
}
