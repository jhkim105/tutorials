package com.example.oauth2;

import static java.util.Arrays.asList;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuthTest {


  @LocalServerPort
  private int port;

  @Test
  public void testResourceOwnerPassword() {
    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
    resourceDetails.setUsername("user01");
    resourceDetails.setPassword("pass01");
    resourceDetails.setAccessTokenUri(String.format("http://localhost:%s/oauth/token", port));
    resourceDetails.setClientId("client01");
    resourceDetails.setClientSecret("secret01");
//    resourceDetails.setScope(asList("read", "write"));
    DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    log.debug("accessToken:{}",  restTemplate.getAccessToken().getValue());
  }

  @Test
  public void testResourceOwnerPassword2() {
    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
    resourceDetails.setUsername("user01");
    resourceDetails.setPassword("pass01");
    resourceDetails.setAccessTokenUri(String.format("http://localhost:%s/oauth/token", port));
    resourceDetails.setClientId("client02");
    resourceDetails.setClientSecret("secret02");
//    resourceDetails.setScope(asList("read", "write"));
    DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    log.debug("accessToken:{}",  restTemplate.getAccessToken().getValue());
  }


  @Test
  public void testClientCredential() {
    ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
    resourceDetails.setAccessTokenUri(String.format("http://localhost:%s/oauth/token", port));
    resourceDetails.setClientId("client01");
    resourceDetails.setClientSecret("secret01");
    DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    log.debug("accessToken:{}",  restTemplate.getAccessToken().getValue());
  }
}