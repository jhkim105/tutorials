package jhkim105.tutorials.testing.contextcaching;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "service.greeting=Hello, TestPropertySource")
class ContextReloadWhenTestPropertySourceTest {

  @Test
  void test() {
    System.out.println("@TestPropertySource");
  }

}
