package org.worldcubeassociation.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class StatisticsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StatisticsApplication.class);
        ConfigurableApplicationContext applicationContext = app.run(args);
        Environment env = applicationContext.getEnvironment();
        log.info("\n----------------------------------------------------------\n"
                + "\tAccess URL:\n\thttp://localhost:{}/swagger-ui.html\n"
                + "----------------------------------------------------------", env.getProperty("server.port"));
    }

}
