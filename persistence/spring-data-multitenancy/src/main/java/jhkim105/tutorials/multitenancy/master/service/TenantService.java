package jhkim105.tutorials.multitenancy.master.service;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.tenant.TenantDataSourceProperties;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final TenantRepository tenantRepository;
  private final TenantDataSourceProperties tenantDataSourceProperties;
  private final TenantDatabaseHelper tenantDatabaseHelper;

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

    tenantDatabaseHelper.createSchema(tenant);

    return tenant;
  }


}
