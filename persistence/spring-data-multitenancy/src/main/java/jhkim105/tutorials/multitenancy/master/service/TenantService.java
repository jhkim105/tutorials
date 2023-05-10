package jhkim105.tutorials.multitenancy.master.service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.tenant.TenantDataSourceProperties;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseHelper;
import jhkim105.tutorials.multitenancy.tenant.migrate.TenantFlywayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.springframework.stereotype.Service;

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
    if (!tenantFlywayProperties.isMigrateOnTenantAdd()) {
      return;
    }

    String[] locations = tenantFlywayProperties.getLocations();
    String baselineVersion = tenantFlywayProperties.getBaselineVersion();

    log.debug("Flyway migrate. location: {}, baselineVersion: {}",
        locations, baselineVersion);
    Flyway flyway = Flyway.configure()
        .dataSource(tenant.getJdbcUrl(), tenant.getDbUsername(), tenant.getDbPassword())
        .locations(locations)
        .baselineOnMigrate(true)
        .baselineVersion(baselineVersion)
        .load();
    flyway.migrate();
  }

  private String getBaselineVersion(Tenant tenant) {
    Flyway flyway = Flyway.configure()
        .dataSource(tenant.getJdbcUrl(), tenant.getDbUsername(), tenant.getDbPassword())
        .locations(tenantFlywayProperties.getLocations()).load();
    MigrationInfoService migrationInfoService = flyway.info();
    MigrationInfo[] migrationInfos = migrationInfoService.all();
    if (migrationInfos != null && migrationInfos.length > 0) {
      MigrationInfo migrationInfo = migrationInfos[migrationInfos.length - 1];
      return migrationInfo.getVersion().getVersion();
    }
    return "1.0";
  }

  public String getLastScriptFileName(String dirPath) {
    File dir = new File(dirPath);
    List<String> fileNameList = Stream.of(Objects.requireNonNull(dir.listFiles()))
        .filter(File::isFile)
        .map(f -> FilenameUtils.getBaseName(f.getName()))
        .sorted(Comparator.reverseOrder())
        .collect(Collectors.toList());
    return fileNameList.get(0);
  }

  public void deleteTenant(Tenant tenant) {
    tenantRepository.delete(tenant);
  }

  public void dropOrphanDatabases() {
    List<String> databaseNames = tenantDatabaseHelper.getTenantDatabaseNames();
    List<String> targetDatabaseNames = databaseNames.stream().filter(this::notExistsTenantByDatabaseName).collect(Collectors.toList());
    if (targetDatabaseNames.isEmpty()) {
      return;
    }

    targetDatabaseNames.stream().forEach(tenantDatabaseHelper::dropDatabase);
  }

  private boolean notExistsTenantByDatabaseName(String databaseName) {
    String tenantName = StringUtils.removeStart(databaseName, Tenant.DATABASE_NAME_PREFIX);
    return !tenantRepository.existsByName(tenantName);
  }

  public Tenant updateTenantName(String tenantId, String newName) {
    Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new);
    tenant.setName(newName);
    return tenantRepository.save(tenant);
  }

  public List<Tenant> findAll() {
    return tenantRepository.findAll();
  }
}
