package com.example.demo;

import com.example.demo.user.IdpConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new IdpConverter());
  }

}
