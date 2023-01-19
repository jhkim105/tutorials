package jhkim105.tutorials.spring.security.jwt.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationErrorHandler {

  private final MappingJackson2HttpMessageConverter jacksonConverter;

  //401: 인증되지 않은(로그인 하지 않은) 경우
  public void handleUnauthorized(HttpServletResponse response) {
    try {
      Map<String, Object> errorAttributes = new HashMap<>();
      errorAttributes.put("status", HttpStatus.UNAUTHORIZED.value());
      errorAttributes.put("message", "Unauthorized");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      jacksonConverter.write(errorAttributes, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new IllegalStateException("jacksonConverter error", e);
    }
  }

  // 403: 인증되었지만 접근권한이 없는 경우
  public void handleAccessDenied(HttpServletResponse response) {
    try {
      Map<String, Object> errorAttributes = new HashMap<>();
      errorAttributes.put("status", HttpStatus.FORBIDDEN.value());
      errorAttributes.put("message", "Forbidden");
      response.setStatus(HttpStatus.FORBIDDEN.value());
      jacksonConverter.write(errorAttributes, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new IllegalStateException("jacksonConverter error", e);
    }
  }
}
