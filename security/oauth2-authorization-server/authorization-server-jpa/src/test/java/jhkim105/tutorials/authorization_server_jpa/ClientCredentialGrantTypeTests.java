package jhkim105.tutorials.authorization_server_jpa;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import jhkim105.tutorials.authorization_server_jpa.service.JpaRegisteredClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class ClientCredentialGrantTypeTests {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  JpaRegisteredClientRepository jpaRegisteredClientRepository;

  RegisteredClient registeredClient;

  @BeforeEach
  void createClient() {
    registeredClient = RegisteredClient
        .withId(UUID.randomUUID().toString())
        .clientId("client02")
        .clientSecret("{noop}secret02")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .scope("read")
        .scope("write")
        .build();
    jpaRegisteredClientRepository.save(registeredClient);
  }

  @AfterEach
  void deleteClient() {
    jpaRegisteredClientRepository.delete(registeredClient);
  }

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
