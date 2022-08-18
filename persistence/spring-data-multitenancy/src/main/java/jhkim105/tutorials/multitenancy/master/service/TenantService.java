package jhkim105.tutorials.multitenancy.master.service;

import java.util.List;
import java.util.stream.Collectors;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.tenant.TenantDataSourceProperties;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseHelper;
import jhkim105.tutorials.multitenancy.tenant.migrate.TenantFlywayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final TenantRepository tenantRepository;
  private final TenantDataSourceProperties tenantDataSourceProperties;
  private final TenantDatabaseHelper tenantDatabaseHelper;
  private final TenantFlywayProperties tenantFlywayProperties;

  public Tenant findById(String id) {
    return tenantRepository.findById(id).orElse(null);
  }

  public Tenant createTenant(String name) {
    Tenant tenant = tenantRepository.findByName(name);
    if (tenant == null) {
      tenant = Tenant.builder()
          .name(name)
          .dbAddress(tenantDataSourceProperties.getAddress())
          .dbUsername(tenantDataSourceProperties.getUsername())
          .dbPassword(tenantDataSourceProperties.getPassword())
          .build();
      tenant = tenantRepository.save(tenant);
    }

    tenantDatabaseHelper.createDatabase(tenant);
    setUpFlywayBaseline(tenant);

    return tenant;
  }

  private void setUpFlywayBaseline(Tenant tenant) {
    if (!tenantFlywayProperties.isEnabled()) {
      return;
    }
    Flyway flyway = Flyway.configure()
        .dataSource(tenant.getJdbcUrl(), tenant.getDbUsername(), tenant.getDbPassword())
        .locations(tenantFlywayProperties.getLocations())
        .baselineOnMigrate(tenantFlywayProperties.isBaselineOnMigrate())
        .baselineVersion(tenantFlywayProperties.getBaselineVersion())
        .load();
    flyway.migrate();
  }

  public void deleteTenant(Tenant tenant) {
    tenantRepository.delete(tenant);
  }

  public void dropOrphanDatabases() {
    List<String> databaseNames = tenantDatabaseHelper.getTenantDatabaseNames();
    List<String> targetDatabaseNames = databaseNames.stream().filter(this::notExistsTenantByDatabaseName).collect(Collectors.toList());
    if (CollectionUtils.isEmpty(targetDatabaseNames)) {
      return;
    }

    targetDatabaseNames.stream().forEach(tenantDatabaseHelper::dropDatabase);
  }

  private boolean notExistsTenantByDatabaseName(String databaseName) {
    String tenantName = StringUtils.removeStart(databaseName, Tenant.DATABASE_NAME_PREFIX);
    return tenantRepository.existsByName(tenantName);
  }

  @Transactional(transactionManager = "transactionManager")
  public Tenant updateTenantName(String oldName, String newName) {
    Tenant tenant = tenantRepository.findByName(oldName);
    tenant.setName(newName);
    return tenantRepository.save(tenant);
  }
}
