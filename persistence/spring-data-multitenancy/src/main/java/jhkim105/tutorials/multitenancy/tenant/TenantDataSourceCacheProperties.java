package jhkim105.tutorials.multitenancy.tenant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TenantDataSourceCacheProperties {
  private int maxSize = 200;
  private int expireMinutes = 10;

}
