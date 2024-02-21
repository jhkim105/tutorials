package jhkim105.tutorials.spring.mvc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "service")
public class ServiceProperties {

  private String storagePath;

}
