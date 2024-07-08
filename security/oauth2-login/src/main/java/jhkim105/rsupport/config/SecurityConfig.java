package jhkim105.rsupport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/login", "/webjars/**", "/favicon.ico", "/error")
        .permitAll()
        .anyRequest()
        .authenticated());

    http.oauth2Login(it -> it.loginPage("/login"));

    return http.build();
  }
}
