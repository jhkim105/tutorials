package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  @SuppressWarnings("PMD.UncommentedEmptyMethod")
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
   }

}
