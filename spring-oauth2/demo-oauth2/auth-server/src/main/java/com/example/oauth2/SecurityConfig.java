package com.example.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user01").password("{noop}pass01").roles("USER")
        .and()
        .withUser("admin").password("{noop}111111").roles("ADMIN");
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean()
      throws Exception {
    return super.authenticationManagerBean();
  }


  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    // @formatter:off
    http.authorizeRequests()
        .antMatchers("/**").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().permitAll();
    // @formatter:on
  }

}
