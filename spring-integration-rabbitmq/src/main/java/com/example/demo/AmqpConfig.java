package com.example.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@EnableRabbit
public class AmqpConfig {

  @Bean
  public Queue foo() {
    return new Queue("foo");
  }

}
