package com.example.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class Junit5Test {

  @Test
  void test() {
    int n = 10;
    assertThat(n).isEqualTo(10);

    assertAll(
        () -> assertEquals(n, 10),
        () -> assertFalse(n == 2)
    );

  }

  @Test
  void assertThrowsException() {
    String str = null;
    assertThrows(IllegalArgumentException.class, () -> {
      Integer.valueOf(str);
    });
  }
}
