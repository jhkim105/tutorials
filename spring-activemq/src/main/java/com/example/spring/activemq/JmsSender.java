package com.example.spring.activemq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsSender {
  private final JmsTemplate jmsTemplate;

  public void send(String queue, String message) {
    jmsTemplate.convertAndSend(queue, message);
  }
}
