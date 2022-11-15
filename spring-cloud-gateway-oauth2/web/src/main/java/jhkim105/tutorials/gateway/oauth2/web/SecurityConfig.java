package jhkim105.tutorials.gateway.oauth2.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final KeycloakLogoutHandler keycloakLogoutHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
    http
        .authorizeHttpRequests(auth -> auth
                .antMatchers("/user-info").hasRole("USER")
                .anyRequest().permitAll());
    http.oauth2Login();
    http.logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"));
    return http.build();
  }


}