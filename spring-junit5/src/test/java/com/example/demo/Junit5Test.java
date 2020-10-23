package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class Junit5Test {

  @Test
  public void test() {
    int n = 10;
    assertThat(n).isEqualTo(10);

    assertAll(
        () -> assertEquals(n, 10),
        () -> assertFalse(n == 2)
    );

  }
}
