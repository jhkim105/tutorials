package com.example.demo;

import com.example.demo.auth.AuthenticationTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
  @Value("${jwt.key}")
  private String jwtKey;

  @Value("${jwt.authTokenExpiry}")
  private int authTokenExpiry;

  @Bean
  public AuthenticationTokenUtil authenticationTokenUtil() {
    return new AuthenticationTokenUtil(jwtKey, authTokenExpiry);
  }
}
