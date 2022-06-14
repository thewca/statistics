package org.worldcubeassociation.statistics.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
import org.worldcubeassociation.statistics.repository.jdbc.SumOfRanksRepository;
import org.worldcubeassociation.statistics.service.EventService;
import org.worldcubeassociation.statistics.service.SumOfRanksService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SumOfRanksServiceImpl implements SumOfRanksService {
    private final SumOfRanksRepository sumOfRanksRepository;
    private final EventService eventService;

    @Override
//    @Transactional // TODO remove
    public void generate() {
        log.info("Generate sum of ranks");
        sumOfRanksRepository.deleteAll();
        sumOfRanksRepository.generateWorldRank();
        sumOfRanksRepository.generateContinentRank();
        sumOfRanksRepository.generateCountryRank();
    }

    @Override
    public List<SumOfRanksDto> list(String regionType, String region, String resultType, int page, int pageSize) {
        return sumOfRanksRepository.list(regionType, region, resultType, page, pageSize);
    }

    @Override
    public SumOfRanksMetaDto meta() {
        SumOfRanksMetaDto response = new SumOfRanksMetaDto();
        response.setAvailableEvents(eventService.getCurrentEvents());
        response.setRegions(sumOfRanksRepository.getRegions());
        return response;
    }
}
