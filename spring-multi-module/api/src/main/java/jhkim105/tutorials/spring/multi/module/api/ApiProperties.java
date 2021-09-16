package jhkim105.tutorials.spring.multi.module.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
@Getter
@Setter
public class ApiProperties {

  private String appName;
  private String appVersion;
}
