package org.worldcubeassociation.statistics.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.repository.jdbc.RankRepositoryJdbc;
import org.worldcubeassociation.statistics.service.RankService;

@Slf4j
@Service
@AllArgsConstructor
public class RankServiceImpl implements RankService {
    private final RankRepositoryJdbc rankRepository;

    @Override
    @Transactional
    public void generateSumOfRanks() {
        log.info("Generate sum of ranks");
        rankRepository.deleteAll();
        rankRepository.generateWorldRank();
        rankRepository.generateContinentRank();
        rankRepository.generateCountryRank();
    }
}
