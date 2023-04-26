package jhkim105.tutorials.spring_security_role.user;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest

class UserServiceTest {


  @Autowired
  UserService userService;


  @Test
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void findAll() {
    userService.findAll();
  }

  @Test
  @WithMockUser(username = "admin", roles = { "ADMIN" })
  void findAll2() {
    userService.findAll2();
  }


  @Test
  @WithMockUser(username = "user01", roles = { "USER" })
  void findAll_roleUser_then_AccessDeniedException() {
    assertThrows(AccessDeniedException.class, () -> userService.findAll());
  }
  @Test
  @WithMockUser(username = "user01", roles = { "USER" })
  void findAll2_roleUser_then_AccessDeniedException() {
    assertThrows(AccessDeniedException.class, () -> userService.findAll2());
  }

}