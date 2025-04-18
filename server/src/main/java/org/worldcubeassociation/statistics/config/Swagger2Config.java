package org.worldcubeassociation.statistics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger2Config {

    @Value("${version}")
    private String version;

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI()
            .info(new Info().title("WCA Statistics API")
                .description("An API for serving WCA Statistics project.")
                .version(version)
                .license(
                    new License().name("GPLv3").url("https://www.gnu.org/licenses/gpl-3.0.html")));
    }
}