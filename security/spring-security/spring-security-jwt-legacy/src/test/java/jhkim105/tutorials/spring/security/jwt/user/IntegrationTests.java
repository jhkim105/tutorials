package jhkim105.tutorials.spring.security.jwt.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private LoginController loginController;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testLogin() throws Exception {
    // when
    LoginRequest loginRequest = new LoginRequest("user", "111111");
    ResultActions resultActions =
        mockMvc.perform(post("/login")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());


    // then
    resultActions.andExpect(status().isOk())
    .andExpect(jsonPath("$.authToken").exists())
    .andExpect(jsonPath("$.refreshToken").exists());
  }

  @Test
  void testMe() throws Exception {
    // given
    LoginRequest loginRequest = new LoginRequest("user", "111111");
    String authToken = loginController.login(loginRequest).getAuthToken();

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
  void testSave() throws Exception {
    // given
    LoginRequest loginRequest = new LoginRequest("user", "111111");
    String authToken = loginController.login(loginRequest).getAuthToken();
    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
    userUpdateRequest.setNickname("변경된닉네임");
    // when
    ResultActions resultActions =
        mockMvc.perform(
            post("/users")
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequest)))
            .andDo(print());

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.nickname").value(userUpdateRequest.getNickname()))
        .andExpect(jsonPath("$.updatedBy").value("1"))
        .andExpect(jsonPath("$.username").exists());

  }




}
