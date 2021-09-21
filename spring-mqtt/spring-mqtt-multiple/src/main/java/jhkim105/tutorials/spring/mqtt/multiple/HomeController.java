package jhkim105.tutorials.spring.mqtt.multiple;

import java.util.Collection;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class HomeController {

  private final MqttConfigUtils mqttConfigUtils;

  private final MqttProperties mqttProperties;

  @Resource(name = "outboundMessageHandlers")
  private Collection<MqttPahoMessageHandler> outboundMessageHandlers;

  private final MqttUtils mqttUtils;

  @GetMapping("/count")
  public ResponseEntity<Integer> getHandlersCount() {
    return ResponseEntity.ok(outboundMessageHandlers.size());
  }


  @GetMapping("/publish")
  public ResponseEntity<Void> publish(String message) {
    mqttUtils.publish("/test", message);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/refresh")
  public ResponseEntity<Void> refresh() {
    outboundMessageHandlers.clear();
    outboundMessageHandlers.add(mqttConfigUtils.outboundMessageHandler(mqttProperties, "tcp://localhost:1883"));
    return ResponseEntity.ok().build();
  }

}
