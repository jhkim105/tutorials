package com.example.multitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class SpringDataMultitenantApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataMultitenantApplication.class, args);
  }

}
