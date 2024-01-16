package com.tutorials.test.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorials.test.domain.User;
import com.tutorials.test.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;



  private User user;

  @BeforeEach
  void setupUser() {
    user = User.createForTest("username01", "password01");
    userRepository.save(user);
  }

  @AfterEach
  void deleteUser() {
    if (userRepository.existsById(user.getId())) {
      userRepository.deleteById(user.getId());
    }
  }

  @Test
  void getAll() throws Exception {
    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void getById() throws Exception {
    mockMvc.perform(get("/users/{id}", user.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.username").value("username01"))
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  void save() throws Exception {
    user.setPassword("pass_changed");
    mockMvc.perform(
            post("/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.password").value("pass_changed"))
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  void deleteById() throws Exception {
    mockMvc.perform(delete("/users/{id}", user.getId()))
        .andDo(print())
        .andExpect(status().isOk());
  }

}