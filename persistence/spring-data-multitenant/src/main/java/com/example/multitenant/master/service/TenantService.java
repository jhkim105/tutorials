package com.example.multitenant.master.service;

import com.example.multitenant.master.domain.Tenant;
import com.example.multitenant.master.domain.TenantUser;
import com.example.multitenant.master.repository.TenantRepository;
import com.example.multitenant.master.repository.TenantUserRepository;
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
