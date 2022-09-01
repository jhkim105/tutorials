package jhkim105.tutorials.multitenancy.tenant.migrate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TenantFlywayProperties {

  private boolean migrateOnServerStart;
  private boolean migrateOnTenantAdd;
  private String[] locations;
  private String baselineVersion;

}
