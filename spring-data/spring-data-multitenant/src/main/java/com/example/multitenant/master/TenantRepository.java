package com.example.multitenant.master;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

  Tenant findFirstBy();
}
