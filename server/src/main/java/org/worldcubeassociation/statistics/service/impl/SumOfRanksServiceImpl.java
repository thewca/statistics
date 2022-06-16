package org.worldcubeassociation.statistics.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.worldcubeassociation.statistics.dto.EventDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksRegionDto;
import org.worldcubeassociation.statistics.exception.InvalidParameterException;
import org.worldcubeassociation.statistics.repository.jdbc.SumOfRanksRepository;
import org.worldcubeassociation.statistics.response.PageResponse;
import org.worldcubeassociation.statistics.service.EventService;
import org.worldcubeassociation.statistics.service.SumOfRanksService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SumOfRanksServiceImpl implements SumOfRanksService {
    private final SumOfRanksRepository sumOfRanksRepository;
    private final EventService eventService;

    @Override
    @Transactional
    public void generate() {
        log.info("Generate sum of ranks");

        deleteExistingData();

        sumOfRanksRepository.generateWorldRank();
        sumOfRanksRepository.generateContinentRank();
        sumOfRanksRepository.generateCountryRank();

        log.info("Update regional ranks");
        sumOfRanksRepository.updateRanks();

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
    public PageResponse<SumOfRanksDto> list(String resultType, String regionType, String region, int page, int pageSize,
                                            String wcaId) {
        if (!StringUtils.isBlank(wcaId)) {
            page = sumOfRanksRepository.getWcaIdPage(resultType, regionType, region, pageSize, wcaId)
                    .orElseThrow(() -> new InvalidParameterException(
                            String.format("WCA ID %s not found for region type %s and region %s", wcaId, regionType,
                                    region)));
        }
        List<SumOfRanksDto> sor = sumOfRanksRepository.list(resultType, regionType, region, page, pageSize);

        // MySql does a very poor job when orgnizing json_arrayagg. We need to correct it here.
        sor.forEach(s -> s.setEvents(s.getEvents().stream().sorted(Comparator.comparing(e -> e.getEvent().getRank()))
                .collect(Collectors.toList())));

        PageResponse<SumOfRanksDto> response = new PageResponse<>();
        response.setPage(page);
        response.setPageSize(pageSize);
        response.setContent(sor);
        return response;
    }

    @Override
    public List<SumOfRanksMetaDto> meta() {
        List<SumOfRanksMetaDto> response = sumOfRanksRepository.getResultTypes();

        // MySql does not a good job when ordering json_arrayagg.
        // We need to make sure that is organized for consistency

        response.forEach(r -> {
            r.setRegionGroups(r.getRegionGroups().stream()
                    .sorted(Comparator.comparing(a -> (a.getRegions().size())))
                    .collect(Collectors.toList()));

            r.getRegionGroups().forEach(rg -> rg.setRegions(rg.getRegions().stream().sorted(Comparator.comparing(
                    SumOfRanksRegionDto::getRegion)).collect(Collectors.toList())));

            r.setAvailableEvents(r.getAvailableEvents().stream().sorted(Comparator.comparing(EventDto::getRank))
                    .collect(Collectors.toList()));
        });

        return response;
    }
}
