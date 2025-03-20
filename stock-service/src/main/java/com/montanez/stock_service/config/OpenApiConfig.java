package com.montanez.stock_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Stock Service API")
                        .version("1.0")
                        .description("API for for music shop stock items"))
                .addServersItem(new Server().url("http://localhost:8080/stock-service"));
    }
}
