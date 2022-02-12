package com.example.multitenant.master.repository;

import com.example.multitenant.master.domain.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantUserRepository extends JpaRepository<TenantUser, String> {
  TenantUser findByUsername(String username);
}
