package jhkim105.tutorials.multitenancy.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tenant.datasource")
@Getter
@Setter
public class TenantDataSourceProperties {
  private String address;
  private String username;
  private String password;
  private int maxTotal = 10;
  private int maxIdle = 8;
  private int minIdle = 0;
  private int initialSize = 0;
  private int expireMinutes = 10;

}
