package com.example.demo.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class AuthUser implements Principal {

  public static final String AUTHORITY_SEPERATOR = ",";

  private String clientId;

  private String username;

  private String authority;

  public Set<GrantedAuthority> getGrantedAuthorities() {

    if (StringUtils.isBlank(this.authority))
      return null;
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    Arrays.stream(StringUtils.split(authority, AUTHORITY_SEPERATOR)).forEach(s -> grantedAuthorities.add((GrantedAuthority) () -> s));
    return grantedAuthorities;
  }

  @Override
  public String getName() {
    return username;
  }
}
