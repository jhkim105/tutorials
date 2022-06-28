package jhkim105.tutorials.multitenancy;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Slf4j
public class SqlTest2 {

  @Autowired
  UserRepository userRepository;

  private String userId;

  @BeforeEach
  void setUp() {
    User user = User.builder()
        .username("user01")
        .build();

    userId = userRepository.save(user).getId();

  }


  @Test
  @Sql(scripts = {"/user.sql"})
  void test() {
    assertThat(userRepository.getById("tid01")).isNotNull();
    assertThat(userRepository.getById(userId)).isNotNull();
  }

}
