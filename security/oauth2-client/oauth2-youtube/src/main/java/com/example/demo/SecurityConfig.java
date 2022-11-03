package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;

@Configuration
//@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private OAuth2AuthenticatoinSuccessHandler oAuth2AuthenticatoinSuccessHandler;

  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**", "/h2-console/**", "/favicon.ico", "/error");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http.authorizeRequests()
        .antMatchers("/login/**").permitAll();

    http
      .oauth2Login()
        .authorizationEndpoint()
          .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/oauth2"))
          .and()
        .successHandler(oAuth2AuthenticatoinSuccessHandler)
        ;

    // @formatter:on
  }


  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public AuthorizationRequestRepository customAuthorizationRequestRepository() {
//    return new HttpSessionOAuth2AuthorizationRequestRepository();
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }

}
