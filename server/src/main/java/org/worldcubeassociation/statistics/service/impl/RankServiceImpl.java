package org.worldcubeassociation.statistics.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.dto.rank.SumOfRanksDto;
import org.worldcubeassociation.statistics.repository.jdbc.RankRepositoryJdbc;
import org.worldcubeassociation.statistics.service.RankService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RankServiceImpl implements RankService {
    private final RankRepositoryJdbc rankRepository;

    @Override
//    @Transactional // TODO remove
    public void generateSumOfRanks() {
        log.info("Generate sum of ranks");
        rankRepository.deleteAll();
        rankRepository.generateWorldRank();
        rankRepository.generateContinentRank();
        rankRepository.generateCountryRank();
    }

    @Override
    public List<SumOfRanksDto> getSumOfRanks(String regionType, String region, String resultType, int page, int pageSize) {
        return rankRepository.getSumOfRanks(regionType, region, resultType, page, pageSize);
    }
}
