package com.tutorials.test.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorials.test.domain.User;
import com.tutorials.test.service.UserService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
class UserControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private User user;

  @BeforeEach
  void setupUser() {
    user = User.createForTest("username01", "password01");
  }

  @Test
  void getAll() throws Exception {
    when(userService.getAll()).thenReturn(Collections.singletonList(user));
    mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void getById() throws Exception {
    when(userService.getById(any(String.class))).thenReturn(user);
    mockMvc.perform(get("/users/id01"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.username").value("username01"))
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  void save() throws Exception {
    when(userService.save(user)).thenReturn(user);
    mockMvc.perform(
            post("/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.username").value("username01"))
        .andExpect(jsonPath("$.id").isNotEmpty());
  }

  @Test
  void deleteById() throws Exception {
    String id = user.getId();
    doNothing().when(userService).deleteById(anyString());
    mockMvc.perform(delete("/users/{id}", id))
        .andDo(print())
        .andExpect(status().isOk());

    verify(userService).deleteById(id);
  }

}