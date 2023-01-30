package jhkim105.tutorials.spring_security_role.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jhkim105.tutorials.spring_security_role.user.domain.Privilege;
import jhkim105.tutorials.spring_security_role.user.domain.Role;
import jhkim105.tutorials.spring_security_role.user.domain.User;
import jhkim105.tutorials.spring_security_role.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;


  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), true, true, true,
        true, getAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    roles.forEach(role -> {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        role.getPrivileges().stream().map(Privilege::name).map(SimpleGrantedAuthority::new).forEach(authorities::add);
    });

    return authorities;
  }

}
