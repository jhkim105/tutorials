package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmqpListener {

  @RabbitListener(queues = AmqpConfig.QUEUE_MESSAGES)
  public void receiveMessage(Message message) {
    log.debug("received: {}", message.getPayload());
    throw new RuntimeException("intended error");
  }
}
