package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String SECURITY_TOKEN = "authToken";

  private static final String SECURITY_TOKEN_HEADER = "Authorization";

  @Autowired
  private JwtAuthenticationTokenService tokenService;

  @Autowired
  private TokenAuthenticationHandler tokenAuthenticationHandler;

  public TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
    super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    setAuthenticationManager(new NoOpAuthenticationManager());
    setAuthenticationSuccessHandler(new TokenAuthenticationSuccessHandler());
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    log.debug("TokenAuthenticationFilter");
    String token = getToken(request);
    if (StringUtils.isBlank(token)) {
      return null;
    }

    AuthUser authUser;
    try {
      authUser = tokenService.parseToken(token);
      return new JwtAuthenticationToken(authUser, authUser.getAuthorities());

    } catch (Exception e) {
      return handleError(request, response);
    }
  }

  private Authentication handleError(HttpServletRequest request, HttpServletResponse response) {
    tokenAuthenticationHandler.handleAccessDenied(request, response);
    return null;
  }


  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String token = getToken(request);
    if (StringUtils.isBlank(token)) {
      chain.doFilter(request, response);
      return;
    }
    super.doFilter(req, res, chain);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    String token = request.getParameter(SECURITY_TOKEN);
    if (StringUtils.isBlank(token)) {
      token = request.getHeader(SECURITY_TOKEN_HEADER);
    }

    return token;
  }
}
