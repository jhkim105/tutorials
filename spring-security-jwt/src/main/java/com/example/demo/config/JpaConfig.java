package com.example.demo.config;

import com.example.demo.security.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
  @Bean
  public SpringSecurityAuditorAware auditorAware() {
    return new SpringSecurityAuditorAware();
  }
}
