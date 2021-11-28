package utils;

import java.io.File;
import java.io.FileOutputStream;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;

public class Downloader {

  public static File download(String url, String path) {
    return RestTemplateUtils.getRestTemplate().execute(url, HttpMethod.GET, null, clientHttpResponse -> {
      File ret = new File(path);
      StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
      return ret;
    });
  }
}
