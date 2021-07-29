package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoadController {
  private  final LoadService loadService;

  @GetMapping("/load")
  public ResponseEntity<?> load(int no) {
    loadService.load(no);
    return ResponseEntity.ok().build();
  }

}
