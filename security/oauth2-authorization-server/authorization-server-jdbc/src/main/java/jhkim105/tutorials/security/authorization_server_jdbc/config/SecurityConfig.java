package jhkim105.tutorials.security.authorization_server_jdbc.config;



import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .anyRequest().authenticated())
        .formLogin(withDefaults());
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
