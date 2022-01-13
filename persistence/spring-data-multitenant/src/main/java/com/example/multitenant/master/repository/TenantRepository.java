package com.example.multitenant.master.repository;

import com.example.multitenant.master.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

  Tenant findFirstBy();
}
