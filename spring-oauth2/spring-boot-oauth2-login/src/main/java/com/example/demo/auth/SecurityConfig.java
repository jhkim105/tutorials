package com.example.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**", "/h2-console", "/favicon.ico");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//        .anyRequest().authenticated()
//        .and()
//        .oauth2Login();
    // @formatter:off
    http
      .authorizeRequests()
        .antMatchers("/login", "/user/register/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
          .loginPage("/login")
        .and()
      .csrf().disable();
    // @formatter:on
  }

  @Bean
  public UserDetailsServiceImpl userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}
