package jhkim105.tutorials.jwt;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@ToString
public class JwtProperties {

  private String key;

}
