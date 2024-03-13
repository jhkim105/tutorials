package com.example.spring.activemq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JmsSenderTest {

  @Autowired
  private JmsSender jmsSender;

  @Test
  public void send() {
    jmsSender.send(JmsQueues.TEST, "Hi");
  }

}