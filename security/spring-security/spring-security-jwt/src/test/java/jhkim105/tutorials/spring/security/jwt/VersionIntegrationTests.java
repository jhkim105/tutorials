package jhkim105.tutorials.spring.security.jwt;

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
class VersionIntegrationTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void shouldReturnVersion() throws Exception {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost")
        .port(port)
        .path("/jwt/version")
        .build().toUri();
    ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri, String.class);
    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
  }


}