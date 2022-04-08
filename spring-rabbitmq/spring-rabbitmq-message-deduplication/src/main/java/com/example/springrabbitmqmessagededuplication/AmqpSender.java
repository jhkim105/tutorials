package com.example.springrabbitmqmessagededuplication;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmqpSender {
  private final RabbitTemplate rabbitTemplate;

  public void sendMessage(String message) {
    Message m = MessageBuilder
        .withBody(message.getBytes(StandardCharsets.UTF_8))
        .build();

    m.getMessageProperties().setHeader("x-deduplication-header", message.hashCode());
//    m.getMessageProperties().setHeader("x-cache-ttl", 5000);
    rabbitTemplate.send(AmqpConfig.QUEUE_DD, m);
  }


}
