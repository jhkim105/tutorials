package jhkim105.tutorials.spring.security.form_login.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import jhkim105.tutorials.spring.security.form_login.user.User;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString(exclude = {"password"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

  public static final String AUTHORITY_SEPARATOR = ",";

  private String id;

  private String authority;

  private String username;

  @JsonIgnore
  private String password;

  private Set<GrantedAuthority> authorities;

  public UserPrincipal(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.authorities = new HashSet<>();
    user.getRoles().stream().forEach(authority -> authorities.add((GrantedAuthority) authority::name));
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
