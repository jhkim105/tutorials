package jhkim105.tutorials.spring.cloud.function.aws.functional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
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
