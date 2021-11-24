package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Wrapper;
import org.apache.catalina.webresources.ExtractingRoot;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EmbeddedTomcatConfig {

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
    return container -> container.addContextCustomizers(ctx -> {
      ctx.setResources(new ExtractingRoot());
      ctx.setReloadable(false);
      Wrapper jsp = (Wrapper)ctx.findChild("jsp");
      jsp.addInitParameter("development", "false");
      log.info("servletContainerCustomizer executed");
    });
  }
}