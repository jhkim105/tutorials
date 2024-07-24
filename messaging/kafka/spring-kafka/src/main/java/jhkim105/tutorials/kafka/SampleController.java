package jhkim105.tutorials.kafka;



import jhkim105.tutorials.kafka.KafkaTopicConfig.Topics;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

  private final KafkaSender sender;

  @GetMapping("/send")
  public void send() {
    SampleMessage sampleMessage = SampleMessage.of("Hello Kafka");
    sender.send(Topics.SAMPLE, sampleMessage);
  }


}
