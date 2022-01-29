package jhkim105.tutorials.spring.data.envers.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jhkim105.tutorials.spring.data.envers.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  void findById() throws JsonProcessingException {
    User user = userRepository.findById("id01").get();
    log.debug("{}", user.getId());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    log.debug("{}", objectMapper.writeValueAsString(user));
  }

  @Test
  void findAll() {
    log.debug("{}", userRepository.findAll());
  }
}