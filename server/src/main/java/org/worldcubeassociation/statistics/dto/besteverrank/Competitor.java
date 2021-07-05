package org.worldcubeassociation.statistics.dto.besteverrank;

public interface Competitor extends Comparable<Competitor> {
    String getWcaId();

    ResultsDTO getSingle();

    ResultsDTO getAverage();
}
