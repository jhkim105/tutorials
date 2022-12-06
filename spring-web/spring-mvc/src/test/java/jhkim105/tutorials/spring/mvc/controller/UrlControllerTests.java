package jhkim105.tutorials.spring.mvc.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class UrlControllerTests {


  @Autowired
  MockMvc mockMvc;

  @Test
  void getServerUrl() throws Exception {
    mockMvc.perform(get("/url/server-url"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("http://localhost/url"));
  }

  @Test
  void getServerUrl2() throws Exception {
    mockMvc.perform(get("/url/server-url2"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("http://localhost/url/server-url2"));
  }


}
