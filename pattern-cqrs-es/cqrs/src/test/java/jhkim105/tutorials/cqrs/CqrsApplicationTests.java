package jhkim105.tutorials.cqrs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.cqrs.command.CreateUserCommand;
import jhkim105.tutorials.cqrs.command.DeleteUserCommand;
import jhkim105.tutorials.cqrs.command.UpdateUserCommand;
import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.repository.UserReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(OrderAnnotation.class)
class CqrsApplicationTests {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserReadRepository userWriteRepository;

  private String username = "testuser01";
  private String usernameNew = "testuser01-new";

  @Test
  @Order(1)
  void createUser() throws Exception {
    CreateUserCommand command = new CreateUserCommand(username);
    String jsonString = objectMapper.writeValueAsString(command);
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
        .andDo(print())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.username").value(username));
  }

  @Test
  @Order(2)
  void updateUser() throws Exception {
    User user = userWriteRepository.findByUsername(username).get();
    UpdateUserCommand command = new UpdateUserCommand(user.getId(), usernameNew);

    mockMvc.perform(put("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(command)))
        .andDo(print())
        .andExpect(jsonPath("$.id").value(user.getId()))
        .andExpect(jsonPath("$.username").value(usernameNew));

  }

  @Test
  @Order(3)
  void deleteUser() throws Exception {
    User user = userWriteRepository.findByUsername(usernameNew).get();
    DeleteUserCommand command = new DeleteUserCommand(user.getId());

    mockMvc.perform(delete("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(command)))
        .andDo(print())
        .andExpect(status().isOk());

  }

}
