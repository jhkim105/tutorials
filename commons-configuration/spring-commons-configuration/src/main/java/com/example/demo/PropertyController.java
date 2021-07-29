package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PropertyController {

  private final DatabaseConfiguration databaseConfiguration;

  @GetMapping("/property")
  public String getProperty(String key) {
    return (String)databaseConfiguration.getProperty(key);
  }
}
