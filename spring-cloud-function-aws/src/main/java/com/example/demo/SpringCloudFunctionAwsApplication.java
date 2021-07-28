package com.example.demo;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudFunctionAwsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudFunctionAwsApplication.class, args);
  }


  @Bean
  public Function<String, String> uppercase() {
    System.out.println();
    return param -> {
      System.out.println("param:" + param);
      return param.toUpperCase();};
  }
}
