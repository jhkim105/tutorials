package com.example.demo.config;

import com.example.demo.security.AuthUser;
import com.example.demo.security.SecurityUtils;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> Optional.ofNullable(SecurityUtils.getAuthUserSilently())
        .map(AuthUser::getId);
  }
}
