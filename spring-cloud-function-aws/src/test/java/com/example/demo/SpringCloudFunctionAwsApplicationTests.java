package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class SpringCloudFunctionAwsApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void uppercase() throws Exception {
    MvcResult result = this.mockMvc.perform(
        post("/uppercase")
            .contentType(MediaType.TEXT_PLAIN)
            .content("this is a cloud function"))
        .andDo(print())
        .andReturn();

    mockMvc.perform(asyncDispatch(result))
        .andExpect(content().string("THIS IS A CLOUD FUNCTION"));
  }

  @Test
  public void greeter() throws Exception {
    MvcResult result = this.mockMvc.perform(
        post("/greeter")
            .contentType(MediaType.TEXT_PLAIN)
            .content("Cloud Function"))
        .andDo(print())
        .andReturn();

    mockMvc.perform(asyncDispatch(result))
        .andExpect(content().string("Hello Cloud Function"));
  }

}
