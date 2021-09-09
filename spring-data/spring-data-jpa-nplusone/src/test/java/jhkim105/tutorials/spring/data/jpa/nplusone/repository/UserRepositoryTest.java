package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.nplusone.JpaConfig;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  void findAllUsingEntityGraph() {
    Page<User> userPage = userRepository.findAllUsingEntityGraph(PageRequest.of(0, 2));
    userPage.getContent().stream().forEach(user -> user.getCoupons().size());
    userPage.getContent().stream().forEach(user -> user.getOrders().size());
  }

  @Test
  void findAllUsingQuery() {
    List<User> users = userRepository.findAllUsingQuery();
    users.stream().forEach(user -> user.getCoupons().size());
    users.stream().forEach(user -> user.getOrders().size());
  }

}
