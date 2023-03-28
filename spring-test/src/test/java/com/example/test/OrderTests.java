package com.example.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class OrderTests {

  @Test
  @Order(1)
  void first() {
    log.info("first");
  }

  @Test
  @Order(2)
  void second() {
    log.info("second");
  }
}
