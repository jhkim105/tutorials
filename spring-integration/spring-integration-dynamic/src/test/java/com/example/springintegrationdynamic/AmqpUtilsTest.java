package com.example.springintegrationdynamic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AmqpUtilsTest {

  @Autowired
  AmqpUtils amqpUtils;

  @Test
  void deleteQueue() {
    amqpUtils.deleteQueues();
  }
}