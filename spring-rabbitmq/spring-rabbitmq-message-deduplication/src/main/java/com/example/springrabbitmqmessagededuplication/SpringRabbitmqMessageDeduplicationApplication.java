package com.example.springrabbitmqmessagededuplication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class SpringRabbitmqMessageDeduplicationApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringRabbitmqMessageDeduplicationApplication.class, args);
  }

  private final AmqpSender amqpSender;

  @GetMapping
  public ResponseEntity<String> send(String message) {
    amqpSender.sendMessage(message);
    return ResponseEntity.ok("sent");
  }


}
