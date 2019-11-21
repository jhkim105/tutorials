package com.example.demo.config;


import com.example.demo.security.TokenAuthenticationEntryPoint;
import com.example.demo.security.TokenAuthenticationFilter;
import com.example.demo.security.TokenAuthenticationProvider;
import com.example.demo.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(        "/error"
//        , "/swagger-ui.html", "/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs/**"
        , "/h2-console", "/h2-console/**"
        , "/favicon.ico");
  }

  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .httpBasic().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
          .antMatchers("/users/login", "/users/join").permitAll()
          .anyRequest().authenticated()
          .and()
                .addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class)
        .authenticationProvider(tokenAuthenticationProvider())
        .exceptionHandling().authenticationEntryPoint(tokenAuthenticationEntryPoint()).and()
        .csrf().disable();
    // @formatter:on
  }

  @Bean
  public UserDetailsServiceImpl userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider()).authenticationProvider(tokenAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter("/**");
    return tokenAuthenticationFilter;
  }

  @Bean
  public TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint() {
    return new TokenAuthenticationEntryPoint();
  }

  @Bean
  public TokenAuthenticationProvider tokenAuthenticationProvider() {
    return new TokenAuthenticationProvider();
  }

}
