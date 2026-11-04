package com.clients.clients.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger-settings.server-url:http://localhost:8081}")
    private String url;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Client Platform API")
                        .description("REST API для управлениями клиентами в Лиге")
                        .version("1.0"))
                .addServersItem(new Server()
                        .url(url)
                        .description("Local server"));
    }
}
