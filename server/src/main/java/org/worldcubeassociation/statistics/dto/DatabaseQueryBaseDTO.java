package org.worldcubeassociation.statistics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DatabaseQueryBaseDTO {
    private List<String> headers;    // Column names
    private List<List<String>> content;
}
