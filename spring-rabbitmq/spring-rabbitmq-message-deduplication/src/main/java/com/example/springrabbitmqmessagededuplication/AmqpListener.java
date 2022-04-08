package com.example.springrabbitmqmessagededuplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AmqpListener {

//  @RabbitListener(queues = AmqpConfig.QUEUE_DD, concurrency = "1")
  public void handle(String message) {
    log.info("received:{}", message);
  }
}
