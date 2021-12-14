package com.example.demo.file;

import java.io.File;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
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

    InputStreamResource inputStreamResource = new FilenameAwareInputStreamResource(multipartFile.getInputStream(), multipartFile.getSize(), multipartFile.getOriginalFilename());

    body.add("file", inputStreamResource);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.postForObject(url, requestEntity, String.class);
    return ResponseEntity.ok(result);
  }


  private static class FilenameAwareInputStreamResource extends InputStreamResource {
    private final String filename;
    private final long contentLength;

    public FilenameAwareInputStreamResource(InputStream inputStream, long contentLength, String filename) {
      super(inputStream);
      this.filename = filename;
      this.contentLength = contentLength;
    }

    @Override
    public String getFilename() {
      return filename;
    }

    @Override
    public long contentLength() {
      return contentLength;
    }
  }
}
