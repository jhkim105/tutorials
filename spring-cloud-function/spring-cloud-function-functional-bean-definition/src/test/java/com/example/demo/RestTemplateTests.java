package com.example.demo;

import java.net.URI;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

/**
 * https://cloud.spring.io/spring-cloud-function/reference/html/spring-cloud-function.html#_testing_functional_applications
 */
//@FunctionalSpringBootTest
public class RestTemplateTests {
  @Autowired
  private TestRestTemplate rest;

  @Test
  @Disabled("Error: No qualifying bean of type 'org.springframework.boot.test.web.client.TestRestTemplate' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}")
  void test() throws Exception {
    ResponseEntity<String> result = this.rest.exchange(
        RequestEntity.post(new URI("/uppercase")).body("hello"), String.class);
    System.out.println(result.getBody());
  }
}
