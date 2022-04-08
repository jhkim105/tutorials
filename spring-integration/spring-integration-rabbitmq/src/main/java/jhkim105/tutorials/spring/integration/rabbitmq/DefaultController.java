package jhkim105.tutorials.spring.integration.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DefaultController {

  private final OutboundConfig.RabbitGateway rabbitGateway;

  @RequestMapping(name = "/")
  public ResponseEntity<?> home() {
    rabbitGateway.sendToRabbit("GoGo");
    return ResponseEntity.ok().build();
  }
}
