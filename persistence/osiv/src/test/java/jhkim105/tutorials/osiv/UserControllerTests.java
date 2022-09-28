package jhkim105.tutorials.osiv;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jhkim105.tutorials.osiv.domain.User;
import jhkim105.tutorials.osiv.repository.UserRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MockMvc mockMvc;

  private String username = "testuser01";
  private String userId;

  @BeforeEach
  void setUp() {
    User user = new User(username);
    userRepository.save(user);
    userId = user.getId();
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteById(userId);
  }


  @Test
  void getOne_LazyInitializationException() throws Exception{
      mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId))
          .andExpect(status().is5xxServerError());

  }


  @Test
  void getOne_entityGraph() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/users/entity-graph/" + userId))
        .andExpect(jsonPath("$.username").value(username))
        .andExpect(jsonPath("$.roles", containsInAnyOrder("USER", "ADMIN")));

  }

  @Test
  void getOne_fetchJoin() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/users/fetch-join/" + userId))
        .andExpect(jsonPath("$.username").value(username))
        .andExpect(jsonPath("$.roles", containsInAnyOrder("USER", "ADMIN")));
  }


}

