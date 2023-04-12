package jhkim105.tutorials.spring.webclient;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
class MainControllerTests {

  @Autowired
  WebClient webClient;

  @Autowired
  private WebTestClient webTestClient;


  @Test
  void testGet() {
    webTestClient.get()
        .uri("/")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .consumeWith(response ->
            Assertions.assertThat(response.getResponseBody()).isNotNull());
  }

}
