package jhkim105.tutorials.kafka;



import jhkim105.tutorials.kafka.config.KafkaTopicConfig.Topics;
import jhkim105.tutorials.kafka.dto.Farewell;
import jhkim105.tutorials.kafka.dto.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SampleController {

  private final KafkaSender sender;

  @GetMapping("/greeting")
  public void greeting() {
    sender.send(Topics.MULTITYPE, new Greeting("Hello", "kafka"));
  }


  @GetMapping("/farewell")
  public void farewell() {
    sender.send(Topics.MULTITYPE, new Farewell("Good Bye", 10));
  }


}
