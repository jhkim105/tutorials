package jhkim105.tutorials.authorization_server;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@Disabled
class PasswordGrantTypeTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  // "error_description":"OAuth 2.0 Parameter: grant_type","error":"unsupported_grant_type"
  void whenGrantTypeIsPassword_then400Error() throws Exception {
    mockMvc.perform(
        post("/oauth2/token")
            .header(HttpHeaders.AUTHORIZATION, basic("client02", "secret02"))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("grant_type", "password")
            .param("scope", "read")
            .param("username", "user01")
            .param("password", "pass01")
            )
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  private String basic(String client, String secret) {
    String in = String.format("%s:%s", client, secret);
    String out = Base64.encodeBase64String(in.getBytes());
    return String.format("Basic %s", out) ;
  }


}
