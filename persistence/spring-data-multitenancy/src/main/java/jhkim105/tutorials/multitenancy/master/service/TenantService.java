package jhkim105.tutorials.multitenancy.master.service;

import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.domain.TenantUser;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.master.repository.TenantUserRepository;
import jhkim105.tutorials.multitenancy.tenant.TenantDataSourceProperties;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utils.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final TenantRepository tenantRepository;
  private final TenantUserRepository tenantUserRepository;
  private final TenantDataSourceProperties tenantDataSourceProperties;
  private final TenantDatabaseHelper tenantDatabaseHelper;

  public Tenant findById(String id) {
    log.debug("findById");
    return tenantRepository.findById(id).orElse(null);
  }

  public Tenant findByUsername(String username) {
    TenantUser tenantUser = tenantUserRepository.findByUsername(username);
    if (tenantUser == null) {
      return null;
    }
    return tenantUser.getTenant();
  }

  public Tenant createTenant(String email) {
    String domain = StringUtils.getEmailDomain(email);
    Tenant tenant = Tenant.builder()
        .id(domain)
        .name(domain)
        .dbAddress(tenantDataSourceProperties.getAddress())
        .dbUsername(tenantDataSourceProperties.getUsername())
        .dbPassword(tenantDataSourceProperties.getPassword())
        .build();
    tenant = tenantRepository.save(tenant);
    tenantDatabaseHelper.createSchema(tenant);
    return tenant;
  }



}
