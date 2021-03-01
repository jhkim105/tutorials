package com.example.demo;

import com.example.demo.auth.CustomAuthenticationSuccessHandler;
import com.example.demo.auth.CustomOAuth2UserService;
import com.example.demo.auth.CustomOidcUserService;
import com.example.demo.auth.JwtAuthenticationEntryPoint;
import com.example.demo.auth.JwtAuthenticationFilter;
import com.example.demo.auth.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;
  private final CustomOidcUserService customOidcUserService;
  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**", "/h2-console", "/favicon.ico", "/error");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .authorizeRequests()
        .antMatchers("/login", "/user/register/**", "/oauth-login*", "/oauth2/authorize").permitAll()
        .anyRequest().authenticated()
        .and()
      .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        .and()
//      .logout()
//        .logoutUrl("/logout")
//        .logoutSuccessUrl("/")
//        .and()
      .oauth2Login()
        .loginPage("/oauth-login")
        .userInfoEndpoint()
          .userService(customOAuth2UserService)
          .oidcUserService(customOidcUserService)
          .and()
        .authorizationEndpoint()
          .authorizationRequestRepository(customAuthorizationRequestRepository())
          .and()
//        .defaultSuccessUrl("/login-success")
        .successHandler(customAuthenticationSuccessHandler)
        ;
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    // @formatter:on
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    return new JwtAuthenticationFilter("/**", authenticationManagerBean());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public AuthorizationRequestRepository customAuthorizationRequestRepository() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
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
