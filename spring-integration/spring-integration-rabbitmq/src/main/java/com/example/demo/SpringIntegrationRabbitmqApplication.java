package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class SpringIntegrationRabbitmqApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringIntegrationRabbitmqApplication.class, args);
  }

}
