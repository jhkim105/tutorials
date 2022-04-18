package jhkim105.tutorials.spring.mvc.file;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import jhkim105.tutorials.spring.mvc.AppProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import utils.FileUtils;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

  private final AppProperties appProperties;

  @PostMapping
  public ResponseEntity<UploadResponse> upload(MultipartRequest multipartRequest) {
    MultipartFile multipartFile = multipartRequest.getFile("file");
    if (multipartFile.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    String baseDir = String.format("upload/%s/%s",
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), UUID.randomUUID());
    String uploadPath = appProperties.getStoragePath() + "/" + baseDir;
    File file = FileUtils.upload(multipartFile, uploadPath);
    String uri = baseDir + "/" + file.getName();
    return ResponseEntity.ok(
        UploadResponse.builder()
            .downloadUrl(downloadUrl(uri))
            .build());
  }

  private String downloadUrl(String uri) {
    return String.format("%s/%s",
        StringUtils.removeEnd(MvcUriComponentsBuilder.fromController(this.getClass()).build().toUri().toString(), "/upload"), uri);
  }

  @Getter
  public static class UploadResponse {
    private final String downloadUrl;


    @Builder
    public UploadResponse(String downloadUrl) {
      this.downloadUrl = downloadUrl;
    }

  }

}
