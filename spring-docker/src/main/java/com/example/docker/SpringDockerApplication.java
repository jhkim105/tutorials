package com.example.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDockerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDockerApplication.class, args);
  }

  @GetMapping("/")
  public String home() {
    return "Hello Docker";
  }
}
