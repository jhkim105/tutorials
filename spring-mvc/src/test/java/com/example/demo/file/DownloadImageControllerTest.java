package com.example.demo.file;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
class DownloadImageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void download() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get("/download/image/sample/jpg/1.jpg")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.IMAGE_JPEG));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.IMAGE_JPEG));


  }

}