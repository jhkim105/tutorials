package jhkim105.tutorials.multitenant.master.service;

import jhkim105.tutorials.multitenant.master.domain.Tenant;
import jhkim105.tutorials.multitenant.master.domain.TenantUser;
import jhkim105.tutorials.multitenant.master.repository.TenantRepository;
import jhkim105.tutorials.multitenant.master.repository.TenantUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final TenantRepository tenantRepository;
  private final TenantUserRepository tenantUserRepository;

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

}
