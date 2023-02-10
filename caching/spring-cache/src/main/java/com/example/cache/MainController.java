package com.example.cache;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  private final DateService dateService;

  public MainController(DateService dateService) {
    this.dateService = dateService;
  }

  @GetMapping
  public String get() {
    return dateService.getDateString("mm:ss SSS");
  }

  @DeleteMapping
  public void evict() {
    dateService.evictCache();
  }

}
