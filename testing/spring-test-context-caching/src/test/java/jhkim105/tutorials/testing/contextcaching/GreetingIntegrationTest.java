package jhkim105.tutorials.testing.contextcaching;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GreetingIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void greet() throws Exception {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost")
        .port(port)
        .path("/greeting")
        .build().toUri();
    System.out.println(uri);
    ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri, String.class);
    System.out.println(responseEntity.getStatusCode().value());
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
  }

}