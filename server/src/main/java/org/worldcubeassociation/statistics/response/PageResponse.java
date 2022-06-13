package org.worldcubeassociation.statistics.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private int page;
    private int pageSize;
    private List<T> content;
}
