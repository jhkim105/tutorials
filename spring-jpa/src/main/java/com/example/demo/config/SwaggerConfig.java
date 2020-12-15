package com.example.demo.config;

import io.swagger.annotations.Api;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("actions")
        .alternateTypeRules(AlternateTypeRules.newRule(Pageable.class, Page.class))
        .useDefaultResponseMessages(false)
        .apiInfo(appInfo())
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo appInfo() {
    return new ApiInfoBuilder()
        .title("API")
        .description("Api Documentation")
        .version("0.0.1")
        .build();
  }

  @Bean
  public Docket dataApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("resources")
        .useDefaultResponseMessages(false)
        .apiInfo(appInfo())
        .select()
        .paths(PathSelectors.ant("/data/**"))
        .build();
  }

  @Getter
  static class Page {
    private int page;
    private int size;
    private List<String> sort;
  }
}
