package jhkim105.tutorials.spring.security.form_login;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PasswordEncoderTests {

  @Autowired
  PasswordEncoder passwordEncoder;


  @Test
  void test() {
    String rawPassword = "111111";
    String encPassword = passwordEncoder.encode(rawPassword);
    assertTrue(passwordEncoder.matches(rawPassword, encPassword));
  }
}
