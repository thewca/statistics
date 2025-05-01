package org.worldcubeassociation.statistics.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("statistics-control")
public interface StatisticsControlController {

    @PostMapping
    void start(@NotBlank String path);

    @PatchMapping
    void complete(@NotBlank String path);

    @PostMapping("error")
    void error(@NotBlank String path, String message);
}
