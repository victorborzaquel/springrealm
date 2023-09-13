package com.victorborzaquel.springrealm.config.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI defaultOpenApiConfig() {
    return new OpenAPI()
        .info(new Info().title("Victor's Springrealm: A Jornada Java")
            .description("API de Jogo RPG desenvolvida com Spring Boot.")
            .version("1.0.0"));
  }
}
