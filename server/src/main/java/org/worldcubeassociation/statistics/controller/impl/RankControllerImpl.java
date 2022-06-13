package org.worldcubeassociation.statistics.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.RankController;
import org.worldcubeassociation.statistics.service.RankService;

@RestController
@AllArgsConstructor
public class RankControllerImpl implements RankController {

    private RankService rankService;

    @Override
    public void generateSumOfRanks() {
        rankService.generateSumOfRanks();
    }
}
