package org.worldcubeassociation.statistics.repository.jdbc;

public interface RankRepositoryJdbc {
    void generateWorldRank();

    void deleteAll();

    void generateContinentRank();

    void generateCountryRank();
}
