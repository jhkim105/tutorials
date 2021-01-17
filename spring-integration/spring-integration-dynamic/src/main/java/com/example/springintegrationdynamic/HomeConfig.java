package com.example.springintegrationdynamic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeConfig {

  private final RabbitTemplate rabbitTemplate;

  @GetMapping("/message")
  public ResponseEntity<?> home(String queueName, int count) {
    test(queueName, count);
    return ResponseEntity.ok().build();
  }

  private void test(String queueName, int count) {
    List<String> list = Arrays.asList("id01", "id02", "id03", "id04", "id05", "id06", "id07", "id08", "id09", "id10");
    IntStream.range(0, count).parallel().forEach( i -> {
      Collections.shuffle(list);
      String id = list.get(0);
      log.debug("id:{]", id);
      rabbitTemplate.convertAndSend(queueName, new MessageDto(id));
      rabbitTemplate.convertAndSend(IntegrationConfig.QUEUE_NAME+ "_0", new MessageDto(list.get(0)));
    });
  }
}
