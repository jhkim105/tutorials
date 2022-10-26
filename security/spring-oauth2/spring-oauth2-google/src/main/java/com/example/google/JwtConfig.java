package com.example.google;

import com.example.google.auth.AuthenticationTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
  @Value("${app.jwt.key}")
  private String jwtKey;

  @Value("${app.jwt.authTokenExpiry}")
  private int authTokenExpiry;

  @Bean
  public AuthenticationTokenUtil authenticationTokenUtil() {
    return new AuthenticationTokenUtil(jwtKey, authTokenExpiry);
  }
}
