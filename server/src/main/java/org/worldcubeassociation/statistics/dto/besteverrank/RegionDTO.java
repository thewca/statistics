package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegionDTO implements Comparable<RegionDTO> {
    private String name;
    private List<Integer> singles;
    private List<Integer> averages;

    private List<Competitor> competitors;

    public RegionDTO(String name) {
        this.name = name;
        this.singles = new ArrayList<>();
        this.averages = new ArrayList<>();
        this.competitors = new ArrayList<>();
    }

    @Override
    public int compareTo(RegionDTO o) {
        return name.compareTo(o.getName());
    }
}