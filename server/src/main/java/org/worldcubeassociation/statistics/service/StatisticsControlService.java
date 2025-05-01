package org.worldcubeassociation.statistics.service;

import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.worldcubeassociation.statistics.enums.StatisticsControlStatus;
import org.worldcubeassociation.statistics.exception.NotFoundException;
import org.worldcubeassociation.statistics.model.StatisticsControl;
import org.worldcubeassociation.statistics.repository.StatisticsControlRepository;

@Service
public class StatisticsControlService {

    private final StatisticsControlRepository repository;
    private static final int MAX_MESSAGE_LENGTH = 200;

    public StatisticsControlService(StatisticsControlRepository repository) {
        this.repository = repository;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public StatisticsControl start(String path) {
        var statisticsControl = new StatisticsControl();
        statisticsControl.setPath(path);
        statisticsControl.setCreatedAt(LocalDateTime.now());
        statisticsControl.setStatus(StatisticsControlStatus.STARTED.name());
        return repository.save(statisticsControl);
    }

    private StatisticsControl findExisting(String path) {
        return repository.findById(path)
            .orElseThrow(
                () -> new NotFoundException("StatisticsControl not found for path: " + path));
    }

    public void complete(String path) {
        var statisticsControl = findExisting(path);
        complete(statisticsControl);
    }

    public void complete(StatisticsControl statisticsControl) {
        statisticsControl.setCompletedAt(LocalDateTime.now());
        statisticsControl.setStatus(StatisticsControlStatus.COMPLETED.name());
        repository.save(statisticsControl);
    }

    public void error(String path, String message) {
        var statisticsControl = findExisting(path);
        error(statisticsControl, message);
    }

    public void error(StatisticsControl statisticsControl, String message) {
        statisticsControl.setMessage(StringUtils.abbreviate(message, MAX_MESSAGE_LENGTH));
        statisticsControl.setStatus(StatisticsControlStatus.FAILED.name());
        repository.save(statisticsControl);
    }
}
