package com.tutorials.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringTestApplication.class, args);
  }

}
