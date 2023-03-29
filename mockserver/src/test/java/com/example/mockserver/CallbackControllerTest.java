package com.example.mockserver;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8888})
@AutoConfigureMockMvc
class CallbackControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void beforeEach(MockServerClient mockServerClient) throws Exception {
    Map<String, Object> responseMessageMap = new HashMap<>();
    responseMessageMap.put("code", "100");
    responseMessageMap.put("message", "callback received");

    mockServerClient
        .when(
            request()
                .withMethod("POST")
                .withPath("/callback_result")
        )
        .respond(
            response()
                .withBody(objectMapper.writeValueAsString(responseMessageMap))
                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
        );
  }

  @Test
  void callback() throws Exception {
    String callback = "http://localhost:8888/callback_result";
    mockMvc
        .perform(
            get("/callback")
                .accept(MediaType.APPLICATION_JSON)
                .param("callback", callback)

        )
        .andDo(print())
        .andExpect(status().isOk())
    ;

  }
}