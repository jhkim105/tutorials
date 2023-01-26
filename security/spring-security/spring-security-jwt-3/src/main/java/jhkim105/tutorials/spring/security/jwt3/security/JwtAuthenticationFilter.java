package jhkim105.tutorials.spring.security.jwt3.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String SECURITY_TOKEN = "authToken";

  private static final String SECURITY_TOKEN_HEADER = "Authorization";

  private final JwtAuthenticationTokenService tokenService;

  public JwtAuthenticationFilter(String defaultFilterProcessesUrl,
      JwtAuthenticationTokenService tokenService, AuthenticationErrorHandler authenticationErrorHandler) {
    super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
    setAuthenticationSuccessHandler(new TokenAuthenticationSuccessHandler());
    setAuthenticationFailureHandler(new TokenAuthenticationFailureHandler(authenticationErrorHandler));
    this.tokenService = tokenService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    log.debug("attemptAuthentication");
    String token = getToken(request);
    if (StringUtils.isBlank(token)) {
      return null;
    }

    UserPrincipal userPrincipal;
    try {
      userPrincipal = tokenService.parseToken(token);
      JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userPrincipal);
      return this.getAuthenticationManager().authenticate(jwtAuthenticationToken);
    } catch (RuntimeException e) {
      throw new InternalAuthenticationServiceException(e.toString(), e);
    }
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    log.debug("doFilter");
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
