package org.worldcubeassociation.statistics.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.SumOfRanksController;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksDto;
import org.worldcubeassociation.statistics.dto.sumofranks.SumOfRanksMetaDto;
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
    public List<SumOfRanksDto> list(String resultType, String regionType, String region, int page, int pageSize) {
        return sumOfRanksService.list(resultType, regionType, region, page, pageSize);
    }

    @Override
    public List<SumOfRanksMetaDto> meta() {
        return sumOfRanksService.meta();
    }
}
