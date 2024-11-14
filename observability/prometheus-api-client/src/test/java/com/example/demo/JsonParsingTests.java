package com.example.demo;

import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
/**
 * https://github.com/json-path/JsonPath
 */
class JsonParsingTests {

  @Test
  void active() throws IOException {
    String jsonString = Files.readString(Paths.get("src/test/resources/prometheus-active.json"));
    log.info(jsonString);
    List<Map<String, Object>> list = JsonPath.parse(jsonString).read("$.data.activeTargets.[*].discoveredLabels");
    log.info("{}", list);
  }
}
