package jhkim105.tutorials.spring.cloud.microservices.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "service")
public class ServiceProperties {

  private String storagePath;

}
