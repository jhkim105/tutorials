package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

  public static void downloadWithJavaIO(String urlStr, String localFilename) {
    try (BufferedInputStream in = new BufferedInputStream(new URL(urlStr).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(localFilename)) {
      byte dataBuffer[] = new byte[1024];
      int bytesRead;
      while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
        fileOutputStream.write(dataBuffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void downloadWithNIOFiles(String fileUrl, String filePath) {
    try (InputStream inputStream = new URL(fileUrl).openStream()) {
      Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void downloadWithNIOChannel(String fileUrl, String filePath) {
    try {
      URL url = new URL(fileUrl);
      try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
          FileOutputStream fileOutputStream = new FileOutputStream(filePath);
          FileChannel fileChannel = fileOutputStream.getChannel()) {
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void downloadWithApacheCommons(String fileUrl, String filePath) {
    int CONNECT_TIMEOUT = 10000;
    int READ_TIMEOUT = 10000;
    try {
      org.apache.commons.io.FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath), CONNECT_TIMEOUT, READ_TIMEOUT);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

}


