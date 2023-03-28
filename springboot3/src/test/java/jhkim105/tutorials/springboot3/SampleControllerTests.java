package jhkim105.tutorials.springboot3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SampleControllerTests {

  @Autowired
  MockMvc mockMvc;


  @Test
  void testGet() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/samples"))
        .andDo(print())
        .andExpect(status().isOk()).andReturn();

    String ret = mvcResult.getResponse().getContentAsString();
    assertThat(ret).isNotBlank();
    log.debug("ret: {}", ret);
  }
}
