package jhkim105.tutorials.rabbitmq;


import static jhkim105.tutorials.rabbitmq.AmqpConfig.QUEUE_SAMPLE;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

  private final AmqpSender amqpSender;

  @GetMapping("/send")
  public void send() {
    SampleMessage sampleMessage = new SampleMessage();
    sampleMessage.setName("Test 01");
    amqpSender.send(QUEUE_SAMPLE, sampleMessage);
  }


}
