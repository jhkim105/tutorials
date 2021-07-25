package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RuntimePropertiesTest {

  @Autowired
  PropertiesConfiguration runtimeProperties;

  @Test
  void test() {
    Assertions.assertThat(runtimeProperties.getString("prop1")).isEqualTo("a");
    Assertions.assertThat(runtimeProperties.getString("prop2")).isEqualTo("b");
  }
}
