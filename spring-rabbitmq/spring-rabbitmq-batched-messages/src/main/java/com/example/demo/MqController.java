package com.example.demo;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@RequestMapping("/mq")
public class MqController {


  @Autowired
  private RabbitTemplate rabbitTemplate;


  @GetMapping("/load")
  public String load(Integer num) {
    if ( num == null)
      num = 1000;
    IntStream.rangeClosed(1, num).parallel().forEach(i -> rabbitTemplate.convertAndSend(AmqpConfig.QUEUE_A, "" + i));
    return String.format("message count:%d sent...");
  }

  @GetMapping("/send")
  public String send(String message) {
    if ( message == null)
      message = LocalDateTime.now().toString();
    rabbitTemplate.convertAndSend(AmqpConfig.QUEUE_A, message);
    return String.format("message [%s] sent...", message);
  }
}
