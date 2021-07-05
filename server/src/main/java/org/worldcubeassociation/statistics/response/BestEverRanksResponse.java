package org.worldcubeassociation.statistics.response;

import lombok.Data;

import java.util.List;

@Data
public class BestEverRanksResponse {
    private String message;
    private List<BestEverRanksEventResponse> events;
}
