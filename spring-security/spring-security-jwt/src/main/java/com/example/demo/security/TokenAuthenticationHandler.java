package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TokenAuthenticationHandler {

  @Autowired
  private MappingJackson2HttpMessageConverter jacksonConverter;

  public void handleAccessDenied(HttpServletRequest request, HttpServletResponse response) {
    try {
      Map<String, Object> errorAttributes = new HashMap<>();
      errorAttributes.put("status", HttpStatus.UNAUTHORIZED.value());
      errorAttributes.put("message", "Unauthorized");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      jacksonConverter.write(errorAttributes, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new RuntimeException("jacksonConverter error", e);
    }
  }

}
