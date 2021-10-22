package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringSampleApplication {

  public static void main(String[] args) {
    log.debug("debug");
    log.debug("info");
    SpringApplication.run(SpringSampleApplication.class, args);
  }

}
