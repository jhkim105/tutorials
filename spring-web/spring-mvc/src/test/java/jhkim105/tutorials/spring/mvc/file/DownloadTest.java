package jhkim105.tutorials.spring.mvc.file;

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
import org.springframework.web.util.UriComponentsBuilder;

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

  @Test
  void downloadWithFileName() {
    String url = "http://localhost:8080/download/file";
    String targetPath = "storage/tmp";

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
    builder.queryParam("basePath", "jpg");
    builder.queryParam("index", "1");
    File file = restTemplate.execute(builder.build().encode().toUri(), HttpMethod.GET, null, clientHttpResponse -> {
      String fileName = clientHttpResponse.getHeaders().getContentDisposition().getFilename();
      File ret = new File(targetPath + File.separator + fileName);
      StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
      log.debug("path:{}", ret.getAbsolutePath());
      return ret;
    });

    Assertions.assertThat(file).isNotNull();

  }

}
