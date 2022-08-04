package jhkim105.tutorials.multitenancy.tenant.migrate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TenantFlywayProperties {

  private boolean enabled;
  private String[] locations;
  private boolean baselineOnMigrate;
  private String baselineVersion;

}
