package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;

import java.util.List;

@RequestMapping("rank")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface RankController {
    @PostMapping("sum-of-ranks")
    void generateSumOfRanks();

    @GetMapping("sum-of-ranks/{regionType}/{region}/{resultType}")
    List<SumOfRanksDto> getSumOfRanks(@PathVariable String regionType, @PathVariable String region,
                                      @PathVariable String resultType, int page, int pageSize);
}
