package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringIntegrationRabbitmqApplicationTests {

  @Autowired(required = false)
  private OutboundConfig.RabbitGateway rabbitGateway;

  @Test
  void sendToRabbit() {
    String result = rabbitGateway.sendToRabbit("aaaa");
    log.debug("result:{}", result);
  }

}
