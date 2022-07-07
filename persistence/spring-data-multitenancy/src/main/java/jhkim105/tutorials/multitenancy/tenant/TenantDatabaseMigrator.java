package jhkim105.tutorials.multitenancy.tenant;

import java.util.List;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

@Slf4j
@RequiredArgsConstructor
public class TenantDatabaseMigrator {


  private final TenantRepository tenantRepository;
  private final TenantFlywayProperties tenantFlywayProperties;


  void migrate() {
    if (!tenantFlywayProperties.isEnabled()) {
      return;
    }
    log.info("migrate tenant");
    List<Tenant> tenantList = tenantRepository.findAll();
    tenantList.stream().forEach(this::migrate);
  }

  private void migrate(Tenant tenant) {
    log.info("migrate for [tenantId: {}, tenantName: {}]", tenant.getId(), tenant.getName());
    Flyway flyway = Flyway.configure()
        .dataSource(tenant.getJdbcUrl(), tenant.getDbUsername(), tenant.getDbPassword())
        .locations(tenantFlywayProperties.getLocations())
        .baselineOnMigrate(tenantFlywayProperties.isBaselineOnMigrate())
        .baselineVersion(tenantFlywayProperties.getBaselineVersion())
        .load();

    flyway.migrate();
  }

}
