package jhkim105.tutorials.testing.contextcaching;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = MyApplicationContextInitializer.class)
class ContextReloadingWhenContextConfigurationTest {


  @Test
  void test() {
    System.out.println("@ContextConfiguration");
  }
}
