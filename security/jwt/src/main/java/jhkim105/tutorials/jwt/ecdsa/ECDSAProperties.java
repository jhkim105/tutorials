package jhkim105.tutorials.jwt.ecdsa;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecdsa")
@Getter
@Setter
@ToString
public class ECDSAProperties {

  private String key;

}
