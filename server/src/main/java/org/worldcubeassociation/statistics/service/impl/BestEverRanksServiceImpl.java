package org.worldcubeassociation.statistics.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.dto.besteverrank.BestEverRankDTO;
import org.worldcubeassociation.statistics.repository.BestEverRanksRepository;
import org.worldcubeassociation.statistics.service.BestEverRanksService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestEverRanksServiceImpl implements BestEverRanksService {
    @Autowired
    private BestEverRanksRepository bestEverRanksRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<BestEverRankDTO> get(String personId) {
        return bestEverRanksRepository.findByPersonId(personId).stream()
                .map(it -> objectMapper.convertValue(it, BestEverRankDTO.class)).collect(
                        Collectors.toList());
    }
}
