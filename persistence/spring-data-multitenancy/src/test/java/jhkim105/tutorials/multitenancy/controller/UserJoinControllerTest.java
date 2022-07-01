package jhkim105.tutorials.multitenancy.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
class UserJoinControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void join() throws Exception {
    ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "user01"))
        .andDo(MockMvcResultHandlers.print());

    result.andExpect(status().isOk());
  }
}