package org.worldcubeassociation.statistics.dto;

import lombok.Data;

import java.util.List;

@Data
public class DatabaseQueryBaseDTO {
    private List<String> headers; // Column names
    private List<List<String>> content;
}
