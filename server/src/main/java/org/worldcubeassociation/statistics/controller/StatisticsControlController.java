package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("statistics-control")
public interface StatisticsControlController {

    @PostMapping
    void start(String path);

    @PatchMapping
    void complete(String path);

    @PostMapping("error")
    void error(String path, String errorMessage);
}
