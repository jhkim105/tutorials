package com.example.demo.auth;

import java.util.HashSet;
import java.util.Set;

public enum Authority {
  ADMIN, USER;

  public static Set<Authority> getDefaultAuthorites() {
    Set<Authority> authorities = new HashSet<>();
    authorities.add(ADMIN);
    authorities.add(USER);
    return authorities;
  }
}
