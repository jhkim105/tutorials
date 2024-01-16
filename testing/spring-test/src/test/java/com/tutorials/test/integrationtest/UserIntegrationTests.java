package com.tutorials.test.integrationtest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.tutorials.test.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTests {

  @LocalServerPort
  private int port;

  @Value("#{servletContext.contextPath}")
  private String contextPath;

  @Value("${spring.mvc.servlet.path}")
  private String servletPath;

  @Autowired
  private TestRestTemplate restTemplate;

  private static HttpHeaders headers;


  @BeforeAll
  public static void init() {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
  }

  @Test
  @Sql(statements = "INSERT INTO users(id, username, password) VALUES ('id01', 'user01', 'pass01')",
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(statements = "DELETE FROM users WHERE id='id01'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void getAll() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    var url = UriComponentsBuilder.fromUriString("http://localhost")
        .port(port)
        .path(contextPath)
        .path(servletPath)
        .path("/users")
        .toUriString();
    ResponseEntity<List<User>> response = restTemplate.exchange(
        url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<User>>() {
        });
    List<User> users = response.getBody();

    assertThat(users).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(users.size()).isEqualTo(1);
  }

}
