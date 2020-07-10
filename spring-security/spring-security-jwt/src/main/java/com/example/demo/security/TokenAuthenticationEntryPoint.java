package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Autowired
  private TokenAuthenticationHandler tokenAuthenticationHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
    log.debug("TokenAuthenticationEntryPoint::path:{}, message:{}", request.getRequestURI(), authException.getMessage());
    tokenAuthenticationHandler.handleAccessDenied(request, response);
  }
}
