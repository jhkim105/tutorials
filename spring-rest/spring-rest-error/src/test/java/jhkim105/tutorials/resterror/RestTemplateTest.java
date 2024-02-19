package jhkim105.tutorials.resterror;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;
import java.net.URI;
import java.util.Collections;
import jhkim105.tutorials.resterror.error.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTemplateTest {
  @LocalServerPort
  private int port;

  RestTemplate restTemplate = new RestTemplate();


  @Test
  void get_error_400() {
    String url = "http://localhost:" + port;
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

    String responseBody = null;
    int errorStatusCode = 0;
    String errorCode = null;
    try {
      ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
      responseBody = responseEntity.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException ex) {
      errorStatusCode = ex.getStatusCode().value();
      errorCode = JsonPath.parse(ex.getResponseBodyAsString()).read("$.code");
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    assertThat(responseBody).isNull();
    assertThat(errorStatusCode).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(errorCode).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND.getCode());
  }

  @Test
  void get_error_500() {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost").port(port).path("/123")
        .build()
        .toUri();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

    String responseBody = null;
    int errorStatusCode = 0;
    String errorCode = null;
    try {
      ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
      responseBody = responseEntity.getBody();
    } catch (HttpClientErrorException | HttpServerErrorException ex) {
      errorStatusCode = ex.getStatusCode().value();
      errorCode = JsonPath.parse(ex.getResponseBodyAsString()).read("$.code");
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    assertThat(responseBody).isNull();
    assertThat(errorStatusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(errorCode).isEqualTo(ErrorCode.UNKNOWN.getCode());
  }


}
