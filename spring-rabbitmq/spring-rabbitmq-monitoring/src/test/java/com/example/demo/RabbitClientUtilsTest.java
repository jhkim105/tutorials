package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RabbitClientUtilsTest {

  @Autowired
  RabbitClientUtils rabbitClientUtils;

  @Test
  void unackedCount() {
    log.info("unackedCount:{}", rabbitClientUtils.getUnackedCount());
  }

  @Test
  void queueCount() {
    log.info("queueCount:{}", rabbitClientUtils.getQueueCount());
  }
}
