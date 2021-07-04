package org.worldcubeassociation.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.worldcubeassociation.statistics.controller.BestEverRanksController;
import org.worldcubeassociation.statistics.request.BestEverRanksRequest;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class StatisticsApplication {

    @Value("${server.port}")
    private int port;

    @Autowired
    private BestEverRanksController bestEverRanksController;

    @PostConstruct
    private void message() {
        log.info("\n----------------------------------------------------------\n"
                + "\tAccess URL:\n"
                + "\thttp://localhost:{}/swagger-ui/index.html\n"
                + "----------------------------------------------------------", port);

        // mock
        BestEverRanksRequest request = new BestEverRanksRequest();
        List<String> eventIds = new ArrayList<>();
        eventIds.add("555bf");
        request.setEventIds(eventIds);
        bestEverRanksController.generate(request);
    }

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

}
