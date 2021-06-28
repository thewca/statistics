package org.worldcubeassociation.statistics.dto.besteverrank;

import java.util.List;

public interface Region {
    String getName();

    List<Competitor> getCompetitors();

    List<Integer> getSingles();
}
