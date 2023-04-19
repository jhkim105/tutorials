package jhkim105.tutorials.spring.mqtt.multiple;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class HomeController {
  private final MqttProperties mqttProperties;

  private final MqttUtils mqttUtils;

  @GetMapping("/publish")
  public ResponseEntity<Void> publish(String message) {
    mqttUtils.publish("test", message);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/refresh")
  public ResponseEntity<Void> refresh() {
    mqttUtils.reloadMqttServers();
    return ResponseEntity.ok().build();
  }

}
