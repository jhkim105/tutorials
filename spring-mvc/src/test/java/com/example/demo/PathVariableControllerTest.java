package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
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
public class PathVariableControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Disabled("404")
  public void testUri() throws Exception{
    String uri = "abc/123";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get(String.format("/path/uri/%s", uri))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.uri").value(uri));
  }

  @Test
  @Disabled
  public void testUri2() throws Exception{
    String uri = "abc/123/file.m3u8";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get(String.format("/path/uri2/%s", uri))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.uri").value(uri));
  }

  @Test
  public void testFile() throws Exception{
    String file = "file.m3u8";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get(String.format("/path/file/%s", file))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.file").value(file));
  }

  @Test
  public void testFile2() throws Exception{
    String file = "file.m3u8";
    String path = String.format("a/b/c/%s", file);
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get(String.format("/path/file/%s", path))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.file").value(file))
        .andExpect(jsonPath("$.path").value(path));
  }

}