package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttHandler {

  public void handle(String message) {
    log.debug(message);
  }
}
