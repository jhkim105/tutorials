package jhkim105.tutorials.config.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app2")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
@ToString
// not refresh
public class App2Properties {
  private final String name;

}
