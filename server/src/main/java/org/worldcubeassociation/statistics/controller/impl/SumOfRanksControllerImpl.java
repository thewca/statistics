package org.worldcubeassociation.statistics.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.SumOfRanksController;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
import org.worldcubeassociation.statistics.request.sumofranks.SumOfRanksListRequest;
import org.worldcubeassociation.statistics.response.PageResponse;
import org.worldcubeassociation.statistics.response.rank.SumOfRanksResponse;
import org.worldcubeassociation.statistics.service.AuthorizationService;
import org.worldcubeassociation.statistics.service.SumOfRanksService;

import java.util.List;

@RestController
@AllArgsConstructor
public class SumOfRanksControllerImpl implements SumOfRanksController {

    private final SumOfRanksService sumOfRanksService;

    private final AuthorizationService authorizationService;

    @Override
    public SumOfRanksResponse generate() {
        authorizationService.disableInProd();
        return sumOfRanksService.generate();
    }

    @Override
    public PageResponse<SumOfRanksDto> list(String resultType, String regionType, String region,
                                            SumOfRanksListRequest request) {
        return sumOfRanksService.list(resultType, regionType, region, request);
    }

    @Override
    public List<SumOfRanksMetaDto> meta() {
        return sumOfRanksService.meta();
    }
}
