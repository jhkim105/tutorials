package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void search() throws Exception {
    // when
    ResultActions resultActions = mockMvc.perform(get("/search")
        .param("keyword", "클린코드")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print());

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.count", is(1)));
  }

}
