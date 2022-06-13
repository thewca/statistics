package org.worldcubeassociation.statistics.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.RankController;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;
import org.worldcubeassociation.statistics.service.RankService;

import java.util.List;

@RestController
@AllArgsConstructor
public class RankControllerImpl implements RankController {

    private RankService rankService;

    @Override
    public void generateSumOfRanks() {
        rankService.generateSumOfRanks();
    }

    @Override
    public List<SumOfRanksDto> getSumOfRanks(String regionType, String region, String resultType, int page, int pageSize) {
        return rankService.getSumOfRanks(regionType, region, resultType, page, pageSize);
    }
}
