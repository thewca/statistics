package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;

import java.util.List;

@RequestMapping("sum-of-ranks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface SumOfRanksController {
    @PostMapping
    void generate();

    @GetMapping("{resultType}/{regionType}/{region}")
    List<SumOfRanksDto> list(@PathVariable String resultType, @PathVariable String regionType,
                             @PathVariable String region, int page, int pageSize);

    @GetMapping("meta")
    List<SumOfRanksMetaDto> meta();
}
