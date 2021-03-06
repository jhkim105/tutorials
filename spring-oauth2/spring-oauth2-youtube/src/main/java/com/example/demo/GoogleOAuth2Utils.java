package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth2Utils {

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  private String clientSecret;

  private final RestTemplate restTemplate;

  public GoogleProfile googleProfile(String idTokenValue) {
    if (StringUtils.isBlank(idTokenValue)) {
      throw new RuntimeException("Invalid token");
    }
    GoogleIdTokenVerifier verifier
        = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
        .setAudience(Collections.singletonList(clientId))
        .build();

    GoogleIdToken idToken;
    try {
      idToken = verifier.verify(idTokenValue);
    } catch (GeneralSecurityException | IllegalArgumentException | IOException e) {
      throw new RuntimeException("Invalid token", e);
    }

    if (idToken == null)
      throw new RuntimeException("Invalid token");

    GoogleProfile googleProfile = GoogleProfile.from(idToken.getPayload());
    return googleProfile;
  }

  public GoogleOAuth2TokenResponse refreshAccessToken(String refreshToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.set("client_id", clientId);
    parameters.set("client_secret", clientSecret);
    parameters.set("refresh_token", refreshToken);
    parameters.set("grant_type", "refresh_token");
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
    GoogleOAuth2TokenResponse result = restTemplate.postForObject("https://accounts.google.com/o/oauth2/token", httpEntity, GoogleOAuth2TokenResponse.class);
    log.debug("result->{}", result);
    return result;
  }

  public GoogleOAuth2TokenResponse exchangeToken(String code, String redirectUri) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.set("client_id", clientId);
    parameters.set("client_secret", clientSecret);
    parameters.set("redirect_uri", redirectUri);
    parameters.set("grant_type", "authorization_code");
    parameters.set("code", code);
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
    GoogleOAuth2TokenResponse result = restTemplate.postForObject("https://accounts.google.com/o/oauth2/token", httpEntity, GoogleOAuth2TokenResponse.class);
    return result;
  }

  public String getName(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

    HttpEntity<String> entity = new HttpEntity<>("", headers);

    String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";
    ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
    log.debug("responseBody->{}", response.getBody());
    Map userAttributes = response.getBody();
    return (String)userAttributes.get("name");
  }


  @Getter
  @ToString
  public static class GoogleProfile {
    private String userId;

    private String email;

    private boolean emailVerified;

    private String name;

    public static GoogleProfile from(GoogleIdToken.Payload payload) {
      GoogleProfile googleProfile = new GoogleProfile();
      googleProfile.userId = payload.getSubject();
      googleProfile.email = payload.getEmail();
      googleProfile.name = (String)payload.get("name");
      googleProfile.emailVerified = payload.getEmailVerified();
      return googleProfile;
    }

  }

  @Getter
  @ToString
  public static class GoogleOAuth2TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;
  }

}
