package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringIntegrationRabbitmqDslApplicationTests {

  @Autowired(required = false)
  private OutboundConfig.RabbitGateway rabbitGateway;

  @Test
  void sendToRabbit() {
    rabbitGateway.sendToRabbit("a");
    rabbitGateway.sendToRabbit("b");
//    log.debug("result:{}", result);
  }

}
