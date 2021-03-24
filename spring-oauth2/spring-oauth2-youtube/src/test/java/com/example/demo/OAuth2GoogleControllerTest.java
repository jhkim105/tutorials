package com.example.demo;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.OAuth2GoogleController.OAuth2GoogleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OAuth2GoogleControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected MockMvc mockMvc;


  @Test
  void oauth2google() throws Exception {
    String redirectUri = "http://localhost:3001";
    String code = "4/0AY0e-g6jP4JMOS2Le2ecxrLegAsIcwrFzsbmDtYaOBbRfvHXsOsECKwLsP-9AOjwADVKGA";
    String requestBody = objectMapper.writeValueAsString(OAuth2GoogleRequest.builder().redirectUri(redirectUri).code(code).build());
    this.mockMvc
        .perform(post("/oauth2/google")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }
}