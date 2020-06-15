package com.example.demo.user;

import java.util.HashSet;
import java.util.Set;

public enum Authority {
  ADMIN, USER;

  public static Set<Authority> getFullAuthorites() {
    Set<Authority> authorities = new HashSet<>();
    authorities.add(ADMIN);
    authorities.add(USER);
    return authorities;
  }


  public static Set<Authority> getDefaultAuthorites() {
    Set<Authority> authorities = new HashSet<>();
    authorities.add(ADMIN);
    authorities.add(USER);
    return authorities;
  }
}
