package jhkim105.tutorials.multitenancy.master.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.tenant.TenantDataSourceProperties;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final TenantRepository tenantRepository;
  private final TenantDataSourceProperties tenantDataSourceProperties;
  private final TenantDatabaseHelper tenantDatabaseHelper;
  private final ApplicationEventPublisher eventPublisher;

  public Tenant findById(String id) {
    return tenantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id: " + id));
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

    return tenant;
  }

  public void deleteTenant(Tenant tenant) {
    tenantRepository.delete(tenant);
  }

  public void clearDatabase() {
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
}
