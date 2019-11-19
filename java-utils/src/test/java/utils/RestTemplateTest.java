package utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Slf4j
public class RestTemplateTest {

  @Test
  @Ignore
  public void testGet() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
    String uploadRequestUrl = "http://localhost:8080";
    UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(uploadRequestUrl)
        .queryParam("workCode", "bbbb")
        .build(false);
    RestTemplate restTemplate = new RestTemplate();
    log.debug(uriComponents.toUriString());
    ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, String.class);
  }

}
