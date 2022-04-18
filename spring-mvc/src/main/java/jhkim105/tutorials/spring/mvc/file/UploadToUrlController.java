package jhkim105.tutorials.spring.mvc.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@RestController
@RequestMapping("/uploadToUrl")
@RequiredArgsConstructor
public class UploadToUrlController {


  @PostMapping("/file")
  public ResponseEntity<String> uploadFile(MultipartRequest multipartRequest, String url) throws Exception {
    MultipartFile multipartFile = multipartRequest.getFile("file");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

    File uploadFile = new File("target/" + multipartFile.getOriginalFilename());
    multipartFile.transferTo(uploadFile);
    FileSystemResource fileSystemResource = new FileSystemResource(uploadFile);

    body.add("file", fileSystemResource);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.postForObject(url, requestEntity, String.class);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/stream")
  public ResponseEntity<String> uploadStream(MultipartRequest multipartRequest, String url) throws Exception {
    MultipartFile multipartFile = multipartRequest.getFile("file");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

    MultipartFileResource multipartFileResource = new MultipartFileResource(multipartFile);
    body.add("file", multipartFileResource);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.postForObject(url, requestEntity, String.class);
    return ResponseEntity.ok(result);
  }

  /**
   * org.springframework.web.multipart.MultipartFileResource
   */
  private class MultipartFileResource extends AbstractResource {

    private final MultipartFile multipartFile;

    public MultipartFileResource(MultipartFile multipartFile) {
      this.multipartFile = multipartFile;
    }

    @Override
    public String getFilename() {
      return this.multipartFile.getOriginalFilename();
    }

    @Override
    public String getDescription() {
      return "MultipartFile resource [" + this.multipartFile.getName() + "]";
    }

    @Override
    public long contentLength() {
      return this.multipartFile.getSize();
    }

    @Override
    public InputStream getInputStream() throws IOException {
      return this.multipartFile.getInputStream();
    }

  }

}
