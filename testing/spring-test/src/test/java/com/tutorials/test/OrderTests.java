package com.tutorials.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
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


@TestClassOrder(value = ClassOrderer.OrderAnnotation.class)
class OrderClassTests {
  @Nested
  @Order(1)
  class A {

    @Test
    void testA() {
      System.out.println("testA");
    }
  }

  @Nested
  @Order(2)
  class B {

    @Test
    void testB() {
      System.out.println("testB");
    }

  }
}