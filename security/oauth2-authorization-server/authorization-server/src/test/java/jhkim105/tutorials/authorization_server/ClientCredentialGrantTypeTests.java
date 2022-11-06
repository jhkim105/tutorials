package jhkim105.tutorials.authorization_server;


import static org.assertj.core.api.Assertions.assertThat;
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
class ClientCredentialGrantTypeTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void whenGrantTypIsClientCredentials() throws Exception {
    String response = mockMvc.perform(
        post("/oauth2/token")
            .header(HttpHeaders.AUTHORIZATION, basic("client02", "secret02"))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("grant_type", "client_credentials")
            .param("scope", "read")
            )
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    ;
    assertThat(response).isNotBlank();
  }

  private String basic(String client, String secret) {
    String in = String.format("%s:%s", client, secret);
    String out = Base64.encodeBase64String(in.getBytes());
    return String.format("Basic %s", out) ;
  }


}
