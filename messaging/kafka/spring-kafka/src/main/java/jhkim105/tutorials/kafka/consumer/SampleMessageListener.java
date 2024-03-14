package jhkim105.tutorials.kafka.consumer;

import jhkim105.tutorials.kafka.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleMessageListener {

  @KafkaListener(topics = Topics.SAMPLE)
  public void handle(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
    log.debug("message received. message: {}, partition: {}", message, partition);
  }

}
