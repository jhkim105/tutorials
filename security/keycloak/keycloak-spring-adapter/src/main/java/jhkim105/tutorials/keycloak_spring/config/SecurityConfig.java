package jhkim105.tutorials.keycloak_spring.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@KeycloakConfiguration
@RequiredArgsConstructor
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  private final KeycloakClientRequestFactory keycloakClientRequestFactory;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/admin/**").hasAnyRole("server")
        .anyRequest().authenticated();

  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public KeycloakRestTemplate keycloakRestTemplate() {
    return new KeycloakRestTemplate(keycloakClientRequestFactory);
  }


}
