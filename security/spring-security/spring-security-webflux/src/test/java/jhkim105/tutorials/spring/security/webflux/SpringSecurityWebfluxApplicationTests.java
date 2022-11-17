package jhkim105.tutorials.spring.security.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class SpringSecurityWebfluxApplicationTests {

  @Autowired
  private ApplicationContext context;

  private WebTestClient webTestClient;


  @BeforeEach
  void setup() {
    webTestClient = WebTestClient.bindToApplicationContext(context)
        .configureClient()
        .build();
  }

  @Test
  void whenNoCredentials_thenRedirectToLogin() {
    webTestClient.get()
        .uri("/")
        .header("Accept", "text/html")
        .exchange()
        .expectStatus().is3xxRedirection();
  }

  @Test
  @WithMockUser(username = "user02")
  void whenHasCredentials_thenSeesGreeting() {
    webTestClient.get()
        .uri("/")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Hello, user02");
  }

}
