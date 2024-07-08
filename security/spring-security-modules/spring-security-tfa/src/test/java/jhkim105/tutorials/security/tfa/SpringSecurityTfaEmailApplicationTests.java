package jhkim105.tutorials.security.tfa;

import java.util.HashSet;
import java.util.Set;
import jhkim105.tutorials.security.tfa.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

@SpringBootTest
class SpringSecurityTfaEmailApplicationTests {

  @Test
  void contextLoads() {
  }


  @Test
  void test() {
    Set<GrantedAuthority> authoritySet = new HashSet<>();
    authoritySet.add((GrantedAuthority) () -> Role.ADMIN.name());

//    log.debug("{}", Role.ADMIN::name);

    Set<Role> roles = new HashSet<>();
    roles.add(Role.ADMIN);
    roles.forEach(role -> authoritySet.add((GrantedAuthority)role::name));

  }
}
