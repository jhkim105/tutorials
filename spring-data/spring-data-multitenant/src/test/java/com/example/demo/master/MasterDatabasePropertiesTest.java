package com.example.demo.master;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MasterDatabasePropertiesTest {

  @Autowired
  MasterDatabaseProperties masterDatabaseProperties;

  @Test
  void test() {
    log.debug("{}", masterDatabaseProperties);
  }

}