package jhkim105.tutorials.spring.thymeleaf;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JavaBasicTests {

  @Test
  void test() {
    Role[] roles = Role.values();
    Arrays.stream(roles).forEach(r -> r.setLabel(r.name() + " Label"));
    log.info("roles: {}", Arrays.toString(roles));
  }
}
