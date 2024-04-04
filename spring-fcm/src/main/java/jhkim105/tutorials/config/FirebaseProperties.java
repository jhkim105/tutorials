package jhkim105.tutorials.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {
    private Resource serviceAccount;

}