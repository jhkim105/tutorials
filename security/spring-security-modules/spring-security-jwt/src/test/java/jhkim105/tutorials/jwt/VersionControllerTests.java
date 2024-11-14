package jhkim105.tutorials.jwt;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class VersionControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnVersion() throws Exception {
    this.mockMvc.perform(get("/version"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(not(emptyOrNullString())));
    ;
  }


}