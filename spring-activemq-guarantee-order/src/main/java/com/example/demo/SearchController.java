package com.example.demo;

import com.example.demo.jms.JmsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchService searchService;
  private final JmsSender jmsSender;

  @GetMapping("/search")
  public ResponseEntity<SearchStats> search(String keyword) {
    SearchStats searchStats = searchService.search(keyword);
    return ResponseEntity.ok(searchStats);
  }

  @GetMapping("/search2")
  public ResponseEntity searchByJms(String keyword) {
    jmsSender.send(keyword);
    return ResponseEntity.ok().build();
  }
}
