package jhkim105.tutorials.spring.security.form_login.config;

import jhkim105.tutorials.spring.security.form_login.security.CustomAuthenticationEntryPoint;
import jhkim105.tutorials.spring.security.form_login.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
    http
        .authorizeHttpRequests((authz) -> authz
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint("/login"));
    return http.build();
  }

  @Bean
  protected UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

}