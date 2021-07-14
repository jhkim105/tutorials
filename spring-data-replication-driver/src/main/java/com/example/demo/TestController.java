package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
  private  final LoadService loadService;

  @GetMapping("/load")
  public ResponseEntity<?> loop(int no) {
    loadService.load(no);
    return ResponseEntity.ok().build();
  }

}
