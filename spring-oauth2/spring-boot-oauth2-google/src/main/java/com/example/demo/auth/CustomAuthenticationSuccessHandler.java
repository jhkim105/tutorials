package com.example.demo.auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final AuthenticationService authenticationService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    if (response.isCommitted()) {
      return;
    }
    log.debug("principal->{}", authentication.getPrincipal());

    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    String email = defaultOAuth2User.getAttribute("email");
    String token = authenticationService.generateToken(email);
    String targetUrl = determineTargetUrl(request, response, authentication);
    String redirectionUrl = UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("authToken", token)
        .build().toUriString();
    getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
  }
}
