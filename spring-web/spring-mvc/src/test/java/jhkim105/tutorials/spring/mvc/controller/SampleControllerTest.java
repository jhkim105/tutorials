package jhkim105.tutorials.spring.mvc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.spring.mvc.controller.SampleController.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SampleControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void testPost() throws Exception {
    ResultActions resultActions = mockMvc.perform(post("/sample/post")
            .param("id", "id01")
            .param("name", "name01"))
        .andDo(print());
    String ret = resultActions.andReturn().getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }

  @Test
  void testPostBody() throws Exception {
    Sample sample = Sample.builder()
        .id("id02")
        .name("name02")
        .build();

    ResultActions resultActions = mockMvc.perform(post("/sample/postBody")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sample)))
        .andDo(print());
    String ret = resultActions.andReturn().getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }

}