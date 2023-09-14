package com.victorborzaquel.springrealm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringrealmApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringrealmApplication.class, args);
  }
}
