package com.example.demo.auth;

import com.example.demo.user.User;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString
public class CustomUserDetails implements UserDetails {

  private String password;
  private String username;
  private boolean enabled;
  private Set<GrantedAuthority> authorities;

  private CustomUserDetails(String password, String username, boolean enabled,
      Set<GrantedAuthority> authorities) {
    this.password = password;
    this.username = username;
    this.enabled = enabled;
    this.authorities = authorities;
  }

  public static CustomUserDetails of(User user) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    user.getAuthorities().stream().forEach(authority -> authorities.add((GrantedAuthority) authority::name));
    return new CustomUserDetails(user.getPassword(), user.getEmail(), true, authorities);
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

}
