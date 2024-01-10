package jhkim105.tutorials.spring.security.jwt3.config;

import java.util.Optional;
import jhkim105.tutorials.spring.security.jwt3.security.SecurityUtils;
import jhkim105.tutorials.spring.security.jwt3.security.UserPrincipal;
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
        .map(UserPrincipal::getId);
  }

}
