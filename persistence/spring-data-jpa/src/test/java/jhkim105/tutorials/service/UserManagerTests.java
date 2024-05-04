package jhkim105.tutorials.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserManagerTests {

  @Autowired
  UserManager userManager;


  @Test
  void getById() {
    userManager.getById("id01");
  }


}
