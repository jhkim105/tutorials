package jhkim105.tutorials.spring.cloud.micrservices.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/orders/**").hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.GET, "/api-client/**").permitAll()
                .anyRequest().permitAll())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .build();
  }

}
