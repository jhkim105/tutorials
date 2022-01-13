package com.example.multitenant.master.service;

import com.example.multitenant.master.domain.Tenant;
import com.example.multitenant.master.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final TenantRepository tenantRepository;

  public Tenant findById(String id) {
    log.debug("findById");
    return tenantRepository.findById(id).orElse(null);
  }

}
