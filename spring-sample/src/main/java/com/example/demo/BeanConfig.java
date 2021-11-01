package com.example.demo;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public Sample sample() {
    return new Sample("sample1");
  }

  @Bean
  public Sample sample2() {
    return new Sample("sample2");
  }

  @Getter
  @ToString
  public static class Sample {
    LocalDateTime localDateTime;
    String name;

    public Sample(String name) {
      this.name = name;
      this.localDateTime = LocalDateTime.now();
    }
  }

}
