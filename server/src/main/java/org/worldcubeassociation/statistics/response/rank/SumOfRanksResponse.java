package org.worldcubeassociation.statistics.response.rank;

import lombok.Data;

@Data
public class SumOfRanksResponse {
    private int worldRank;
    private int continentRank;
    private int countryRank;
    private int meta;
}
