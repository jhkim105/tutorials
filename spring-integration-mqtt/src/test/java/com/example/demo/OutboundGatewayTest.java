package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Slf4j
public class OutboundGatewayTest {

  @Autowired(required = false)
  private MqttIntegrationConfig.OutboundGateway outboundGateway;

  @Test
  public void send() {
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss SSS"));
    log.info(time);
    outboundGateway.publish("/test", 2, time);
    log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss SSS")));
  }
}
