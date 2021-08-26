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
import org.junit.jupiter.api.Disabled;
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
@Disabled("java.lang.IllegalAccessException: class io.netty.util.internal.PlatformDependent0$6 cannot access class jdk.internal.misc.Unsafe (in module java.base) because module java.base does not export jdk.internal.misc to unnamed module @769a1df5\n"
    + "\tat java.base/jdk.internal.reflect.Reflection.newIllegalAccessException(Reflection.java:361)\n")
class CallbackControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void beforeEachLifecyleMethod(MockServerClient mockServerClient) throws Exception {
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