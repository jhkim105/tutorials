package com.example.demo.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

@RestController
@RequestMapping("/upload")
public class UploadController {

  @PostMapping
  public ResponseEntity<String> upload(MultipartRequest multipartRequest) {
    MultipartFile multipartFile = multipartRequest.getFile("file");
    return multipartFile.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().build();
  }
}
