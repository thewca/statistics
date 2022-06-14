package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;

import java.util.List;

@RequestMapping("sum-of-ranks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface SumOfRanksController {
    @PostMapping
    void generate();

    @GetMapping("{regionType}/{region}/{resultType}")
    List<SumOfRanksDto> list(@PathVariable String regionType, @PathVariable String region,
                             @PathVariable String resultType, int page, int pageSize);
}
