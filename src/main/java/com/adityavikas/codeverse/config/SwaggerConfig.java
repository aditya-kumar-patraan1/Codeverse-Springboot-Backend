package com.adityavikas.codeverse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPISwagger(){
        return new OpenAPI()
                .info(new Info().
                        title("Codeverse API's")
                        .description("This is the UI for all the API needed for codeverse")
                )
                .servers(List.of(
                        new Server().url("https://codeverse-springboot-backend.onrender.com").description("Production server"),
                        new Server().url("http://localhost:8080").description("Local server"),
                        new Server().url("http://localhost:7080")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }

}
