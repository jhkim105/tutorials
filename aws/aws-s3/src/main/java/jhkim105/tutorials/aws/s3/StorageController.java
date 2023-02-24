package jhkim105.tutorials.aws.s3;


import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{bucketName}")
@Slf4j
public class StorageController {

  private final S3Service s3Service;

  @PostMapping
  public void createBucket(@PathVariable String bucketName) {
    s3Service.createBucket(bucketName);
  }

  @DeleteMapping
  public void deleteBucket(@PathVariable String bucketName){
    s3Service.deleteBucket(bucketName);
  }

  @PostMapping("/**")
  public ResponseEntity<UploadResponse> upload(@PathVariable String bucketName,
      MultipartHttpServletRequest request) {
    String key = extractPath(request);
    MultipartFile multipartFile = request.getFile("file");
    if (multipartFile == null || multipartFile.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    s3Service.upload(bucketName, key, multipartFile);
    String url = String.format("%s%s/%s",
        MvcUriComponentsBuilder.fromController(this.getClass()).build().toUri(),
        bucketName,
        key);

    return ResponseEntity.ok(UploadResponse.of(url));
  }

  @GetMapping("/**")
  public ResponseEntity<Resource> download(@PathVariable String bucketName, HttpServletRequest request) throws IOException {
    String key = extractPath(request);
    log.debug("key: {}", key);
    Resource file = s3Service.loadAsResource(bucketName, key);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
  }

  private String extractPath(HttpServletRequest request) {
    String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String matchPattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE); //
    return new AntPathMatcher().extractPathWithinPattern(matchPattern, path);
  }

  @DeleteMapping("/**")
  public void delete(@PathVariable String bucketName, HttpServletRequest request) {
    String key = extractPath(request);
    s3Service.deleteObject(bucketName, key);
  }

}
