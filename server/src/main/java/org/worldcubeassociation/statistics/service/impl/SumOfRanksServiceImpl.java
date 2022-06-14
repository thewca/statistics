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

        deleteExistingData();

        sumOfRanksRepository.generateWorldRank();
        sumOfRanksRepository.generateContinentRank();
        sumOfRanksRepository.generateCountryRank();

        int newMeta = sumOfRanksRepository.insertMeta();
        log.info("{} meta inserted", newMeta);
    }

    private void deleteExistingData() {
        log.info("Deleting existing data");

        int deletedSor = sumOfRanksRepository.deleteAll();
        log.info("{} SOR deleted", deletedSor);

        int metaDeleted = sumOfRanksRepository.deleteAllMeta();
        log.info("{} meta deleted", metaDeleted);
    }

    @Override
    public List<SumOfRanksDto> list(String regionType, String region, String resultType, int page, int pageSize) {
        return sumOfRanksRepository.list(regionType, region, resultType, page, pageSize);
    }

    @Override
    public SumOfRanksMetaDto meta() {
        SumOfRanksMetaDto response = new SumOfRanksMetaDto();
        response.setAvailableEvents(eventService.getCurrentEvents());
        response.setResultTypes(sumOfRanksRepository.getResultTypes());
        return response;
    }
}
