package com.example.demo.auth;

import com.example.demo.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthUser implements UserDetails {

  public static final String AUTHORITY_SEPERATOR = ",";

  @NonNull
  private String id;

  @NonNull
  private String authority;

  private String username;

  private String password;

  protected boolean enabled;

  private Set<GrantedAuthority> authorities;

  public AuthUser(User user) {
    this.id = user.getId();
    this.username = user.getEmail();
    this.password = user.getPassword();

    this.authorities = new HashSet<>();
    user.getAuthorities().stream().forEach(authority -> authorities.add((GrantedAuthority) authority::name));
    this.authority = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
