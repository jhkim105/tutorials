package jhkim105.tutorials;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SampleMessageListener {

  @RabbitListener(queues = "sample-queue")
  public void listen(SampleMessage sampleMessage) {
    log.debug("Message read from myQueue : " + sampleMessage);
  }

}
