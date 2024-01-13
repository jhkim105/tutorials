package com.tutorials.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringJunit5Application {

  public static void main(String[] args) {
    SpringApplication.run(SpringJunit5Application.class, args);
  }

}
