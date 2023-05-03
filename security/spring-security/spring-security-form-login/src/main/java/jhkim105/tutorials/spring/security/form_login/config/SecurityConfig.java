package jhkim105.tutorials.spring.security.form_login.config;

import jhkim105.tutorials.spring.security.form_login.security.CustomAuthenticationEntryPoint;
import jhkim105.tutorials.spring.security.form_login.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .antMatchers("/", "/home", "/login").permitAll()
            .anyRequest().authenticated())
        .formLogin(FormLoginConfigurer::permitAll)
        .logout(LogoutConfigurer::permitAll)
        .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint("/login"));

    http.sessionManagement(session -> session
        .maximumSessions(1)
//        .maxSessionsPreventsLogin(true)
        .expiredUrl("/login?expired"));

    return http.build();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Bean
  protected UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}