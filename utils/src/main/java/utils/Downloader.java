package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;

public class Downloader {

  public static File download(String url, String dirPath) {
    return RestTemplateUtils.getRestTemplate().execute(url, HttpMethod.GET, null, clientHttpResponse -> {
      String fileName = clientHttpResponse.getHeaders().getContentDisposition().getFilename();
      fileName = StringUtils.defaultString(fileName, UUID.randomUUID().toString());
      File ret = new File(dirPath + File.separator + fileName);
      StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
      return ret;
    });
  }
}
