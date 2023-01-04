package com.example.demo.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsSender {
  private final JmsTemplate jmsTemplate;

  public void send(String keyword) {
    int queueNumber = keyword.hashCode() % JmsQueues.SEARCH_QUEUE_COUNT;
    String queueName = String.format("%s-%s", JmsQueues.SEARCH, queueNumber + 1);
    send(queueName, keyword);
  }

  private void send(String queue, String message) {
    jmsTemplate.convertAndSend(queue, message);
  }
}
