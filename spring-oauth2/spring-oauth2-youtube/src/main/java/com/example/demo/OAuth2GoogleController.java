package com.example.demo;

import com.example.demo.GoogleOAuth2Utils.GoogleOAuth2TokenResponse;
import com.example.demo.GoogleOAuth2Utils.GoogleProfile;
import com.example.demo.OAuthUser.OAuthProvider;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2/google")
@RequiredArgsConstructor
public class OAuth2GoogleController {

  private final GoogleOAuth2Utils googleOAuth2Utils;

  private final OAuthUserRepository oauthUserRepository;


  @PostMapping
  public ResponseEntity<OAuth2GoogleResponse> oauth2google(@RequestBody OAuth2GoogleRequest oauth2GoogleRequest) {
    GoogleOAuth2TokenResponse googleOAuth2TokenResponse = googleOAuth2Utils.exchangeToken(oauth2GoogleRequest.getCode(), oauth2GoogleRequest.getRedirectUri());
    GoogleProfile googleProfile = googleOAuth2Utils.googleProfile(googleOAuth2TokenResponse.getIdToken());
    String userId = googleProfile.getUserId();
    String email = googleProfile.getEmail();
    OAuthUser oauthUser = oauthUserRepository.findByOauthProviderAndUserId(OAuthProvider.GOOGLE, userId);
    if (oauthUser == null) {
      oauthUser = OAuthUser.builder()
          .oauthProvider(OAuthProvider.GOOGLE)
          .userId(userId)
          .email(email)
          .accessToken(googleOAuth2TokenResponse.getAccessToken())
          .expiresIn(googleOAuth2TokenResponse.getExpiresIn())
          .refreshToken(googleOAuth2TokenResponse.getRefreshToken())
          .build();
    }
    oauthUser = oauthUserRepository.save(oauthUser);
    return ResponseEntity.ok(OAuth2GoogleResponse.builder().oauthUserId(oauthUser.getId()).build());
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @ToString
  public static class OAuth2GoogleRequest implements Serializable {

    private static final long serialVersionUID = -7016450456756171603L;
    private String redirectUri;
    private String code;

    @Builder
    public OAuth2GoogleRequest(String redirectUri, String code) {
      this.redirectUri = redirectUri;
      this.code = code;
    }
  }

  @Getter
  @Builder
  @ToString
  public static class OAuth2GoogleResponse implements Serializable {

    private static final long serialVersionUID = 4780737163002677953L;

    private String oauthUserId;

  }


}
