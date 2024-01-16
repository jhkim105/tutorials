package jhkim105.tutorials.spring.mvc.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private String storagePath;

}
