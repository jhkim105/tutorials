package jhkim105.tutorials.multitenancy.tenant.migrate;

import java.util.List;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

@Slf4j
@RequiredArgsConstructor
public class TenantDatabaseMigrator {


  private final TenantRepository tenantRepository;
  private final TenantFlywayProperties tenantFlywayProperties;


  void migrate() {
    if (!tenantFlywayProperties.isMigrateOnServerStart()) {
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
        .load();

    try {
      flyway.migrate();
    } catch(FlywayException ex) {
      log.warn(String.format("Migration Error: %s, Tenant::%s", ex, tenant));
    }
  }

}
