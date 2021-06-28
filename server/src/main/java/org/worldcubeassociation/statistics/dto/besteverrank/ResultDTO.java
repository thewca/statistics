package org.worldcubeassociation.statistics.dto.besteverrank;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResultDTO {
    private LocalDate end;
    private Integer rank;
    private LocalDate start;
    private Integer result;
    private String competition;

    public ResultDTO(Integer result, String competition, LocalDate start) {
        this.result = result;
        this.competition = competition;
        this.start = start;
    }
}
