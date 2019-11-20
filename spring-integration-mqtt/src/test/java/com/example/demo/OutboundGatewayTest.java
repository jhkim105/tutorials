package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OutboundGatewayTest {

  @Autowired(required = false)
  private MqttIntegrationConfig.OutboundGateway outboundGateway;

  @Test
  public void send() {
    outboundGateway.publish("/test", "Hey!");
  }
}
