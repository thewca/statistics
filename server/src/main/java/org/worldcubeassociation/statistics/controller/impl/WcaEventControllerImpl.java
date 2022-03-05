package org.worldcubeassociation.statistics.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import org.worldcubeassociation.statistics.controller.WcaEventController;
import org.worldcubeassociation.statistics.dto.EventDto;
import org.worldcubeassociation.statistics.service.EventService;

import java.util.List;

@RestController
public class WcaEventControllerImpl implements WcaEventController {
    private final EventService eventService;

    public WcaEventControllerImpl(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public List<EventDto> list() {
        return eventService.findAll();
    }
}
