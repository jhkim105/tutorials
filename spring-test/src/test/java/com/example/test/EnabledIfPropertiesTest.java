package com.example.test;


import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;


@Slf4j
@SpringBootTest
@EnabledIf(expression = "#{environment['app.version'] == '2'}", loadContext = true)
class EnabledIfPropertiesTest {

  @Autowired
  AppProperties appProperties;


  @Test
  void test() {
    assertThat(appProperties.getVersion()).isEqualTo("2");
  }


}
