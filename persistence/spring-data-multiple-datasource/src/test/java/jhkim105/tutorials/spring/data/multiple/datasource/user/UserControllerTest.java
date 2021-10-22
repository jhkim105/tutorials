package jhkim105.tutorials.spring.data.multiple.datasource.user;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void findAll() throws Exception {
    ResultActions result = mockMvc.perform(
        MockMvcRequestBuilders.get("/users")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print());
    result.andExpect(status().isOk());
  }
}
