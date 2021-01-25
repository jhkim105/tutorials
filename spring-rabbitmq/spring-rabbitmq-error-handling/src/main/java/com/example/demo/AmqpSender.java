package com.example.demo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmqpSender {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendMessage(String message) {
    rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE_MESSAGES, AmqpConfig.QUEUE_MESSAGES, message);
  }


}
