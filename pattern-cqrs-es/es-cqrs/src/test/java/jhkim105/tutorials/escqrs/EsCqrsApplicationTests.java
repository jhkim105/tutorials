package jhkim105.tutorials.escqrs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.escqrs.command.CreateUserCommand;
import jhkim105.tutorials.escqrs.command.DeleteUserCommand;
import jhkim105.tutorials.escqrs.command.UpdateUserCommand;
import jhkim105.tutorials.escqrs.domain.User;
import jhkim105.tutorials.escqrs.repository.UserReadRepository;
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
class EsCqrsApplicationTests {


  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserReadRepository readRepository;

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
        .andExpect(status().isOk());
  }

  @Test
  @Order(2)
  void updateUser() throws Exception {
    User user = readRepository.findByUsername(username).get();
    UpdateUserCommand command = new UpdateUserCommand(user.getId(), usernameNew);

    mockMvc.perform(put("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(command)))
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  @Order(3)
  void deleteUser() throws Exception {
    User user = readRepository.findByUsername(usernameNew).get();
    DeleteUserCommand command = new DeleteUserCommand(user.getId());

    mockMvc.perform(delete("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(command)))
        .andDo(print())
        .andExpect(status().isOk());

  }
}
