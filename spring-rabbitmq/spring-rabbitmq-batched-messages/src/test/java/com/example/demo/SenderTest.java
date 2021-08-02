package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SenderTest {

  @Autowired
  RabbitTemplate rabbitTemplate;


  @Test
  void send() {
    rabbitTemplate.convertAndSend(AmqpConfig.QUEUE_A, "aaaa");
  }

}
