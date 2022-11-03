package jhkim105.tutorials.oauth2.keycloak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
    http
        .authorizeHttpRequests(auth -> auth
                .antMatchers("/me").hasRole("USER")
                .anyRequest().permitAll());
    http.oauth2Login();
    return http.build();
  }


}