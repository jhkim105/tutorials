package com.example.springrabbitmqmessagededuplication;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringRabbitmqMessageDeduplicationApplicationTests {

  @Autowired
  AmqpSender amqpSender;

  @Test
  void contextLoads() {
  }


  @Test
  void test() {
    log.info("{}", "1".hashCode());
    log.info("{}", "2".hashCode());
  }
}
