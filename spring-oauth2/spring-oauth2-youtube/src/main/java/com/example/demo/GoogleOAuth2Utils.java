package com.example.demo;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
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

  public String refreshAccessToken(String refreshToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.set("client_id", clientId);
    parameters.set("client_secret", clientSecret);
    parameters.set("refresh_token", refreshToken);
    parameters.set("grant_type", "refresh_token");
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);
    Map<String, String> result = restTemplate.postForObject("https://accounts.google.com/o/oauth2/token", httpEntity, Map.class);
    return result.get("access_token");
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

}
