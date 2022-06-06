package org.worldcubeassociation.statistics.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.service.RankService;

@Slf4j
@Service
public class RankServiceImpl implements RankService {
    @Override
    public void generateSumOfRanks() {
        log.info("Generate sum of ranks");

    }
}
