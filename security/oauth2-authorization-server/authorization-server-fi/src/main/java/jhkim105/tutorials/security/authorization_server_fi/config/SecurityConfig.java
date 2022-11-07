package jhkim105.tutorials.security.authorization_server_fi.config;


import jhkim105.tutorials.security.authorization_server_fi.security.FederatedIdentityConfigurer;
import jhkim105.tutorials.security.authorization_server_fi.security.UserRepositoryOAuth2UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    FederatedIdentityConfigurer federatedIdentityConfigurer =
        new FederatedIdentityConfigurer()
            .oauth2UserHandler(new UserRepositoryOAuth2UserHandler());
    http
        .authorizeHttpRequests(authorize ->
            authorize
                .antMatchers("/assets/**", "/webjars/**", "/login").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults())
        .apply(federatedIdentityConfigurer)
    ;
    return http.build();
  }

  @Bean
  UserDetailsService users() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("user01")
        .password("pass01")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

}
