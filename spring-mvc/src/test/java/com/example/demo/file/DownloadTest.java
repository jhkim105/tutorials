package com.example.demo.file;

import java.io.File;
import java.io.FileOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Disabled
class DownloadTest {

  RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    this.restTemplate = new RestTemplate();
  }

  @Test
  void download() {
    String url = "http://localhost:8080/download/image/sample/png/1.png";
    File file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {
      File ret = new File("storage/tmp/1.png");
      StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
      log.debug("path:{}", ret.getAbsolutePath());
      return ret;
    });

    Assertions.assertThat(file).isNotNull();

  }
}
