package com.victorborzaquel.springrealm.config.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
  @Bean
  OpenAPI defaultOpenApiConfig() {
    Info info = new Info();
    
    info.setTitle("Victor's Springrealm: A Jornada Java");
    info.setDescription("API de Jogo RPG desenvolvida com Spring Boot.");
    info.setVersion("0.0.1: Alpha");

    return new OpenAPI().info(info);
  }
}
