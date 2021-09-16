package jhkim105.tutorials.spring.multi.module.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
@Getter
@Setter
public class ServiceProperties {

  private String appName;

}
