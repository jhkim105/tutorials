package jhkim105.tutorials.spring.mvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jhkim105.tutorials.spring.mvc.controller.SampleController.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class SampleControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;


  @Test
  void testGet() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/sample/get")
            .param("id", "id01"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("id01"))
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andReturn();
    assertThat(mvcResult.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    String ret = mvcResult.getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }

  @Test
  void testPost() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/sample/post")
            .param("id", "id01")
            .param("name", "name01"))
        .andDo(print()).andReturn();
    String ret = mvcResult.getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }

  @Test
  void testPostBody() throws Exception {
    Sample sample = Sample.builder()
        .id("id02")
        .name("name02")
        .build();

    MvcResult mvcResult = mockMvc.perform(post("/sample/postBody")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(sample)))
        .andDo(print()).andReturn();
    String ret = mvcResult.getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }

  @Test
  /**
   * configurer.setUseRegisteredSuffixPatternMatch(true);
   * - 등록된 확장자만 허용함
   */
  void testUseRegisteredSuffix_json() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/sample/get.json"))
        .andDo(print())
        .andExpect(status().isOk()).andReturn();
    String ret = mvcResult.getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }

  @Test
  /**
   * configurer.setUseRegisteredSuffixPatternMatch(true);
   * - 모든 확장자를 허용하려면 suffixPatternMatch=true, registeredSuffixPatternMatch=false
   */
  void testUseRegisteredSuffix_other() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/sample/get.do"))
        .andDo(print())
        .andExpect(status().is4xxClientError()).andReturn();
    String ret = mvcResult.getResponse().getContentAsString();
    log.debug("ret: {}", ret);
  }


}