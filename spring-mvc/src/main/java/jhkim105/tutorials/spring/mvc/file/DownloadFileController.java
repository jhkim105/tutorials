package jhkim105.tutorials.spring.mvc.file;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import jhkim105.tutorials.spring.mvc.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.FileUtils;

@RequestMapping("/download/file")
@RestController
@Slf4j
@RequiredArgsConstructor
public class DownloadFileController {

  private final AppProperties appProperties;

  @GetMapping
  public ResponseEntity<InputStreamResource> download(String basePath, int index) {
    log.debug("download:{}", basePath);
    String absoluteBasePath = String.format("%s/files/%s", appProperties.getStoragePath(), basePath);
    List<Path> paths = FileUtils.filePaths(absoluteBasePath);
    Path path = paths.get(index - 1);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader(path.getFileName().toString()))
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(getFileContent(path));
  }

  private String contentDispositionHeader(String fileName) {
    return "attachment;filename=\"" + fileName + "\"";
  }

  private InputStreamResource getFileContent(Path path)  {
    FileSystemResource resource = new FileSystemResource(path);
    try {
      return new InputStreamResource(resource.getInputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
