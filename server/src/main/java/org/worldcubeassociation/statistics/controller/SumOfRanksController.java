package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
import org.worldcubeassociation.statistics.request.sumofranks.SumOfRanksListRequest;
import org.worldcubeassociation.statistics.response.PageResponse;
import org.worldcubeassociation.statistics.response.rank.SumOfRanksResponse;

import java.util.List;
import javax.validation.Valid;

@RequestMapping("sum-of-ranks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface SumOfRanksController {
    @PostMapping
    SumOfRanksResponse generate();

    @GetMapping("{resultType}/{regionType}/{region}")
    PageResponse<SumOfRanksDto> list(@PathVariable String resultType, @PathVariable String regionType,
                                     @PathVariable String region, @Valid SumOfRanksListRequest request);

    @GetMapping("meta")
    List<SumOfRanksMetaDto> meta();
}
