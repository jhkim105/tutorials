package jhkim105.tutorials.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
  String introspectionUri;

  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
  String clientId;

  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
  String clientSecret;

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/products/**").hasAuthority("SCOPE_read")
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(token -> token
                      .introspectionUri(this.introspectionUri)
                      .introspectionClientCredentials(this.clientId, this.clientSecret)))
            .build();
  }

}
