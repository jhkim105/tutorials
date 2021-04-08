package com.example.demo;

import com.example.demo.CustomAuthorizationRequestResolver.StateParameter;
import com.example.demo.OAuthUser.OAuthProvider;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticatoinSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final OAuth2AuthorizedClientService authorizedClientService;

  private final OAuthUserRepository oAuthUserRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    if (response.isCommitted()) {
      return;
    }

    String state = request.getParameter("state");
    String callback = StateParameter.fromKey(state).getCallback();

    OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
    String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
    OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientService.loadAuthorizedClient(registrationId, authentication.getName());
    String accessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
    String refreshToken = oAuth2AuthorizedClient.getRefreshToken() == null ? null : oAuth2AuthorizedClient.getRefreshToken().getTokenValue();

    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
    String userId = defaultOAuth2User.getAttribute("sub");
    String email = defaultOAuth2User.getAttribute("email");
    String name = defaultOAuth2User.getAttribute("name");
    OAuthUser oAuthUser = oAuthUserRepository.findByOauthProviderAndUserId(OAuthProvider.GOOGLE, userId);
    if (oAuthUser == null) {
      oAuthUser = OAuthUser.builder()
          .oauthProvider(OAuthProvider.GOOGLE)
          .userId(userId)
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .email(email)
          .name(name)
          .build();
    }


    String redirectUrl = UriComponentsBuilder.fromUriString(callback)
        .queryParam("oauthUserId", oAuthUser.getId())
        .build().toUriString();
    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
  }
}
