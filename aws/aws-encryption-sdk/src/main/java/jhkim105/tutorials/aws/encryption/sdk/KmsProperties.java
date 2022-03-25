package jhkim105.tutorials.aws.encryption.sdk;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kms", ignoreUnknownFields = false)
@Getter
@Setter
public class KmsProperties {

  private String keyArn;

}
