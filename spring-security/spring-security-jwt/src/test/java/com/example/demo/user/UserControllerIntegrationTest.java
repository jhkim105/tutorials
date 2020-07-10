package com.example.demo.user;

import com.example.demo.common.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Test
  public void testLogin() throws Exception {
    // when
    LoginRequest loginRequest = new LoginRequest("user", "111111");
    ResultActions resultActions =
        mockMvc.perform(post("/users/login")
            .content(JsonUtils.toString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());


    // then
    resultActions.andExpect(status().isOk())
    .andExpect(jsonPath("$.authToken").exists())
    .andExpect(jsonPath("$.refreshToken").exists());
  }

  @Test
  public void testMe() throws Exception {
    // given
    LoginRequest loginRequest = new LoginRequest("user", "111111");
    String authToken = userService.login(loginRequest).getAuthToken();

    // when
    ResultActions resultActions =
        mockMvc.perform(
            get("/users/me")
              .header(HttpHeaders.AUTHORIZATION, authToken)
              .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.username").exists())
        .andExpect(jsonPath("$.authority").exists());

  }

  @Test
  public void testSave() throws Exception {
    // given
    LoginRequest loginRequest = new LoginRequest("user", "111111");
    String authToken = userService.login(loginRequest).getAuthToken();
    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
    userUpdateRequest.setNickname("변경된닉네임");
    // when
    ResultActions resultActions =
        mockMvc.perform(
            post("/users")
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toString(userUpdateRequest)))
            .andDo(print());

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.nickname").value(userUpdateRequest.getNickname()))
        .andExpect(jsonPath("$.updatedBy").value("1"))
        .andExpect(jsonPath("$.username").exists());

  }




}
