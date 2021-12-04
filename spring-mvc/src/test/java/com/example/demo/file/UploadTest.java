package com.example.demo.file;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Disabled
class UploadTest {

  RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    this.restTemplate = new RestTemplate();
  }

  @Test
  void upload() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

    File file = new File("src/test/resources/input.ppt");
    FileSystemResource fileResource = new FileSystemResource(file);
    body.add("file", fileResource);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    String serverUrl = "http://localhost:8080/upload";
    ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
    log.info("Response code: {}", response.getStatusCode());
    log.info("Response body: {}", response.getBody());
  }

}
