package com.example.sprinigconcurrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringConcurrentApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringConcurrentApplication.class, args);
  }

}