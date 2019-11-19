package com.example.demo.file;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DownloadTextControllerTest {
  @Autowired
  private MockMvc mockMvc;


  @Test
  @Ignore
  public void testDownloadText() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get("/download/text")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_OCTET_STREAM));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
  }


}
