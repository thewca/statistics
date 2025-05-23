package org.worldcubeassociation.statistics;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class StatisticsApplication {

    @Value("${server.port}")
    private int port;

    @PostConstruct
    private void message() {
        log.info("\n----------------------------------------------------------\n"
            + "\tAccess URL:\n"
            + "\thttp://localhost:{}/swagger-ui/index.html\n"
            + "----------------------------------------------------------", port);
    }

    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }

}
