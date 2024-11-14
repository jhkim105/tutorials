package jhkim105.tutorials;

import jhkim105.tutorials.config.OutboundConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DefaultController {

  private final OutboundConfig.OutboundGateWay outboundGateWay;
  private final KafkaSender kafkaSender;

  @GetMapping(name = "/")
  public ResponseEntity<?> home() {
    outboundGateWay.send("GoGo");
    return ResponseEntity.ok().build();
  }
}
