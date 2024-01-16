package jhkim105.tutorials.spring.mvc.file;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import jhkim105.tutorials.spring.mvc.common.FileUtils;
import jhkim105.tutorials.spring.mvc.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download/image")
@RequiredArgsConstructor
@Slf4j
public class DownloadImageController {

  private final AppProperties appProperties;

  @GetMapping(value = "/**/{file:.+}")
  public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable String file) {
    log.debug("download:{}", file);
    int index = request.getRequestURI().indexOf("download/image");
    String imageUri = request.getRequestURI().substring(index + "download/image".length() + 1);
    String imagePath = String.format("%s/images/%s", appProperties.getStoragePath(), imageUri);
    log.debug("imagePath:{}", imagePath);
    return ResponseEntity.ok()
        .contentType(MediaType.valueOf(FileUtils.contentType(Paths.get(file))))
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader(FilenameUtils.getName(imagePath)))
        .body(getImage(imagePath));
  }

  private String contentDispositionHeader(String fileName) {
    return ContentDisposition.inline().filename(fileName).build().toString();
  }

  private byte[] getImage(String imagePath) {
    try {
      return Files.readAllBytes(Paths.get(imagePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }




}
