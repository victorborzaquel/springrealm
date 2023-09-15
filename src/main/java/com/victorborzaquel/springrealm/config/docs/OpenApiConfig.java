package com.victorborzaquel.springrealm.config.docs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
  @Bean
  OpenAPI defaultOpenApiConfig() {
    Info info = new Info();

    info.setTitle("Victor's Springrealm: A Jornada Java");
    info.setDescription("API de Jogo RPG desenvolvida com Spring Boot.");
    info.setVersion("0.0.1: Alpha");

    Server localhostServer = new Server();
    localhostServer.setUrl("http://localhost:9988");
    localhostServer.setDescription("Localhost");

    Server productionServer = new Server();
    productionServer.setUrl("https://api.victorborzaquel.com");
    productionServer.setDescription("Production");

    return new OpenAPI()
        .info(info)
        .addServersItem(localhostServer)
        .addServersItem(productionServer);
  }
}
