package org.worldcubeassociation.statistics.dto.besteverrank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResultsDTO {
    private ResultDTO current;
    private ResultDTO bestRank;

    public ResultsDTO(Integer result, String competition, LocalDate date){
        this.current = new ResultDTO(result, competition, date);
        this.bestRank = new ResultDTO(null, null, null);
    }
}
