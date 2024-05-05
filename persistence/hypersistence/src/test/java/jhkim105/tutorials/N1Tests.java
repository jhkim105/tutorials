package jhkim105.tutorials;

import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import jhkim105.tutorials.domain.user.User;
import jhkim105.tutorials.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class N1Tests {

  @Autowired
  UserRepository userRepository;


  @Test
  void testQueryCount() {
    SQLStatementCountValidator.reset();
    User user = userRepository.findById("id01").get();
    SQLStatementCountValidator.assertSelectCount(3);
  }



}
