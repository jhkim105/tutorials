package com.example.springactivemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JmsReceiver {

  @JmsListener(destination = JmsQueues.TEST, concurrency = "1-5")
  public void receiveMessage(String message) {
    log.debug("message:{}", message);
  }
}
