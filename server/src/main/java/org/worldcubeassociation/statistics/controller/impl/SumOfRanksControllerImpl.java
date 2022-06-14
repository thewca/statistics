package org.worldcubeassociation.statistics.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.SumOfRanksController;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;
import org.worldcubeassociation.statistics.service.SumOfRanksService;

import java.util.List;

@RestController
@AllArgsConstructor
public class SumOfRanksControllerImpl implements SumOfRanksController {

    private SumOfRanksService sumOfRanksService;

    @Override
    public void generate() {
        sumOfRanksService.generate();
    }

    @Override
    public List<SumOfRanksDto> list(String regionType, String region, String resultType, int page, int pageSize) {
        return sumOfRanksService.list(regionType, region, resultType, page, pageSize);
    }
}
