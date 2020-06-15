package com.example.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuthClientTest {

  @LocalServerPort
  private int port;

  @Test
  @Ignore
  public void testOAuth() {
    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
    resourceDetails.setUsername("user01");
    resourceDetails.setPassword("pass01");
    resourceDetails.setAccessTokenUri(String.format("http://localhost:%s/oauth/token", 8081));
    resourceDetails.setClientId("client01");
    resourceDetails.setClientSecret("secret01");
    resourceDetails.setGrantType("password");
    DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    String accessToken = restTemplate.getAccessToken().getValue();
    log.debug("accessToken:{}", accessToken);
    String result = restTemplate.getForObject(String.format("http://localhost:%s/posts", port), String.class);
    log.debug("{}", result);
  }
}