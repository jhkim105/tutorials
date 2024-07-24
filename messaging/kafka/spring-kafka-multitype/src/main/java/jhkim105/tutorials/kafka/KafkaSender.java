package jhkim105.tutorials.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, Object message) {
    kafkaTemplate.send(topic, message);
    log.debug("message sent. topic: {}, message: {}", topic, message);
  }


}
