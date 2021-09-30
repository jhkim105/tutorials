package jhkim105.tutorials.spring.http.logging;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
class NoticeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void get() throws Exception {
    ResultActions result = mockMvc.perform(
          MockMvcRequestBuilders.get("/notices")
              .contentType(MediaType.APPLICATION_JSON)
    );

    result.andDo(print());
  }

}