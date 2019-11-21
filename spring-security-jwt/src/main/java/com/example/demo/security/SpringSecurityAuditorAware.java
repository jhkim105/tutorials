package com.example.demo.security;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    AuthUser user = SecurityUtils.getCurrentAuthUserSilently();
    return user == null ? Optional.empty() : Optional.of(user.getId());
  }

}