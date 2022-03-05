package org.worldcubeassociation.statistics.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldcubeassociation.statistics.dto.EventDto;

import java.util.List;

@RequestMapping("event")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public interface WcaEventController {
    @GetMapping
    List<EventDto> list();
}
