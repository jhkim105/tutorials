package com.example.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private static final long serialVersionUID = 1320268163809218040L;

  private final Object principal;



  public JwtAuthenticationToken(Object principal, Set<GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
