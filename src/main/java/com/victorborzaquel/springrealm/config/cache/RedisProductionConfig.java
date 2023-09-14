package com.victorborzaquel.springrealm.config.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("prod")
public class RedisProductionConfig {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  @Value("${spring.data.redis.username}")
  private String username;

  @Value("${spring.data.redis.password}")
  private String password;

  @Bean
  LettuceConnectionFactory lettuceConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);

    config.setUsername(username);
    config.setPassword(password);

    return new LettuceConnectionFactory(config);
  }
}
