package jhkim105.tutorials.reactor;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReactorApplication.class)
@Slf4j
class WebControllerTest {

  @LocalServerPort
  int randomServerPort;

  @Autowired
  WebTestClient testClient;

  @Autowired
  WebController webController;

  @BeforeEach
  void setup() {
    webController.setUserServicePort(randomServerPort);
  }

  @Test
  void getUsersBlocking() {
    EntityExchangeResult<List<User>> result =  testClient.get()
        .uri("/users-blocking")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(User.class)
        .hasSize(2)
        .returnResult();

    List<User> response = result.getResponseBody();
    log.info("{}", response);
  }

  @Test
  void getUsersNonBlocking() {
    EntityExchangeResult<List<User>> result =  testClient.get()
        .uri("/users-non-blocking")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(User.class)
        .hasSize(2)
        .returnResult();

    List<User> response = result.getResponseBody();
    log.info("{}", response);
  }
}