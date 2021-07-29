package com.example.demo;

import java.net.URI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@FunctionalSpringBootTest
@AutoConfigureWebTestClient
class SpringCloudFunctionFunctionalBeanDefinitionApplicationTests {

  @Autowired
  private WebTestClient client;

  @Test
  void uppercase() {
    client.post().uri("/uppercase")
        .body(Mono.just("hello"), String.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).isEqualTo("HELLO");
  }

}
