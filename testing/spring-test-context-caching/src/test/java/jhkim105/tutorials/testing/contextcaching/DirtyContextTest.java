package jhkim105.tutorials.testing.contextcaching;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class DirtyContextTest {



  @DirtiesContext
  void test() {

  }

}
