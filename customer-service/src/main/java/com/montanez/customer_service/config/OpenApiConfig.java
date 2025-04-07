package com.montanez.customer_service.config;

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
                        .title("Customer + Auth Service")
                        .version("1.0")
                        .description("API for customer authentication and profiles"))
                .addServersItem(new Server().url("http://localhost:8080/customer-service"));
    }
}
