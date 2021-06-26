package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegionDTO {
    private String name;
    private List<Integer> singles;
    private List<Integer> averages;

    public RegionDTO(String name) {
        this.name = name;
        this.singles = new ArrayList<>();
        this.averages = new ArrayList<>();
    }
}